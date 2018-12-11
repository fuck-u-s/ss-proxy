#!/system/bin/sh

WORK_PATH=$(dirname $(readlink -f $0))

#echo "$WORK_PATH"

chmod 755 "$WORK_PATH/*"

gpid="`pidof ngov`"
if [ ! -z "$gpid" ]; then
	kill -9 $gpid
fi

IP="$1"
PORT="$2"
USER="$4"
PASSWD="$5"
if [ -z "$USER" ]; then
	USER="aes-256-cfb"
fi
if [ -z "$PASSWD" ]; then
	PASSWD="abc!%40%23123d"
fi

###############################################################################
iptables -t nat -F PREROUTING
iptables -t nat -F REDSOCKS
iptables -t nat -F OUTPUT
###############################################################################
ping -c 1 "$IP"
if [ "$?" = "0" ]; then
    IP="`ping -c 1 "$IP"|head -n 1|cut -d '(' -f 2|cut -d ')' -f 1|awk '{print $1}'`"
fi
############redsocks iptables
iptables -t nat -N REDSOCKS
iptables -t nat -A REDSOCKS -d $IP -j RETURN

# Do not redirect traffic to the followign address ranges
iptables -t nat -A REDSOCKS -d 0.0.0.0/8 -j RETURN
iptables -t nat -A REDSOCKS -d 10.0.0.0/8 -j RETURN
iptables -t nat -A REDSOCKS -d 127.0.0.0/8 -j RETURN
iptables -t nat -A REDSOCKS -d 169.254.0.0/16 -j RETURN
iptables -t nat -A REDSOCKS -d 172.16.0.0/12 -j RETURN
iptables -t nat -A REDSOCKS -d 192.168.0.0/16 -j RETURN
iptables -t nat -A REDSOCKS -d 224.0.0.0/4 -j RETURN
iptables -t nat -A REDSOCKS -d 240.0.0.0/4 -j RETURN
###############################################################################
# Redirect all kinds of traffic
iptables -t nat -A REDSOCKS -p tcp -j REDIRECT --to-ports 10101
iptables -t nat -A REDSOCKS -p udp -j REDIRECT --to-ports 10101
#dns redirect dns server frps on 124.232.152.123

#$WORK_PATH/ngov "-C=$WORK_PATH/ov.conf" "-L=:10100" "-F=quic://IZM:IZM20156@$IP:$PORT" REMARK_SERVER >/dev/null &
if [ "$USER" = "aes-256-cfb" ]; then
    iptables -t nat -A OUTPUT -p udp --dport 53 -j DNAT --to-destination 127.0.0.1:1133
    ndc resolver setnetdns rndis0 "" 8.8.8.8 8.8.4.4
    ndc resolver setnetdns wlan0 "" 8.8.8.8 8.8.4.4
    ndc resolver setnetdns eth0 "" 8.8.8.8 8.8.4.4
    $WORK_PATH/ngov "-C=$WORK_PATH/ov.conf" "-L=:10100" "-F=ss://aes-256-cfb:abc!%40%23123d@$IP:$PORT" REMARK_SERVER >/dev/null &
else
    iptables -t nat -A REDSOCKS -p udp --dport 53 -j RETURN
    ndc resolver setnetdns rndis0 "" 114.114.114.114 223.5.5.5
    ndc resolver setnetdns wlan0 "" 114.114.114.114 223.5.5.5
    ndc resolver setnetdns eth0 "" 114.114.114.114 223.5.5.5
    $WORK_PATH/ngov "-C=$WORK_PATH/ov.conf" "-L=:10100" "-F=socks5://$USER:$PASSWD@$IP:$PORT" REMARK_SERVER >/dev/null &
fi
#$WORK_PATH/ngov "-L=:10100" "-F=ss://aes-256-cfb:abc!%40%23123d@$IP:$PORT" REMARK_SERVER >/dev/null &
echo "boot proxy success"
$WORK_PATH/ngov "-L=redirect://:10101" "-F=gost://127.0.0.1:10100" REMARK_CLIENT >/dev/null &
echo "boot redirector success"

isempty=0
if [ ! -z "$3" ] ; then
    for ownerid in $3
    do
        if [ ! -z "$ownerid" ]; then
            echo "$ownerid"
            iptables -t nat -m owner --uid-owner $ownerid -A OUTPUT -p tcp -j REDSOCKS
            isempty=1
        fi
    done
fi

if [ "$isempty" -eq "0" ]; then
    iptables -t nat -A OUTPUT -p tcp  -j REDSOCKS
    iptables -t nat -A OUTPUT -p udp  -j REDSOCKS
fi

###############################################################################

