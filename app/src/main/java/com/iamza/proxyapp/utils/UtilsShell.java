package com.iamza.proxyapp.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * ShellUtils
 * <ul>
 * <strong>Check root</strong>
 * <li>{@link UtilsShell#checkRootPermission()}</li>
 * </ul>
 * <ul>
 * <strong>Execte command</strong>
 * <li>{@link UtilsShell#execCommand(String, boolean)}</li>
 * <li>{@link UtilsShell#execCommand(String, boolean, boolean)}</li>
 * <li>{@link UtilsShell#execCommand(List, boolean)}</li>
 * <li>{@link UtilsShell#execCommand(List, boolean, boolean)}</li>
 * <li>{@link UtilsShell#execCommand(String[], boolean)}</li>
 * <li>{@link UtilsShell#execCommand(String[], boolean, boolean)}</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-16
 */
public class UtilsShell {

	//public static final String SU_NAME       = "xu";
	
    public static final String COMMAND_SU       = "xu";
    //public static final String COMMAND_SU       = SU_NAME;
    public static final String COMMAND_SH       = "sh";
    public static final String COMMAND_EXIT     = "exit\n";
    public static final String COMMAND_LINE_END = "\n";

    /**
     * check whether has root permission
     * 
     * @return
     */
    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }
    
    /**
     * check whether has root permission
     * 
     * @return
     */
    public static boolean checkRootPermissionSilent() {
        boolean isOK = false;
        
        try {
            String pathstr = System.getenv("PATH");
            String[] paths = pathstr.split(":");
            for (String path : paths) {
                File f = new File(path, COMMAND_SU);
                if (f.exists()) {
                    isOK = true;
                    break;
                }
            }
        } catch (Exception e) {
        }

        return isOK;
    }

    /**
     * execute shell command, default return result msg
     * 
     * @param command command
     * @param isRoot whether need to run with root
     * @return
     * @see UtilsShell#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(String command, boolean isRoot) {
        return execCommandNormal(new String[] { command }, isRoot, true);
    }
    public static CommandResult execCommandCache(String command, boolean isRoot) {
    	return execCommandCache(new String[] { command }, isRoot, true);
    }

    /**
     * execute shell commands, default return result msg
     * 
     * @param commands command list
     * @param isRoot whether need to run with root
     * @return
     * @see UtilsShell#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot) {
        return execCommandNormal(commands == null ? null : commands.toArray(new String[] {}), isRoot, true);
    }
    public static CommandResult execCommandCache(List<String> commands, boolean isRoot) {
    	return execCommandCache(commands == null ? null : commands.toArray(new String[] {}), isRoot, true);
    }

    /**
     * execute shell commands, default return result msg
     * 
     * @param commands command array
     * @param isRoot whether need to run with root
     * @return
     * @see UtilsShell#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot) {
        return execCommandNormal(commands, isRoot, true);
    }
    public static CommandResult execCommandCache(String[] commands, boolean isRoot) {
    	return execCommandCache(commands, isRoot, true);
    }

    /**
     * execute shell command
     * 
     * @param command command
     * @param isRoot whether need to run with root
     * @param isNeedResultMsg whether need result msg
     * @return
     * @see UtilsShell#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommandNormal(new String[] { command }, isRoot, isNeedResultMsg);
    }
    public static CommandResult execCommandCache(String command, boolean isRoot, boolean isNeedResultMsg) {
    	return execCommandCache(new String[] { command }, isRoot, isNeedResultMsg);
    }

    /**
     * execute shell commands
     * 
     * @param commands command list
     * @param isRoot whether need to run with root
     * @param isNeedResultMsg whether need result msg
     * @return
     * @see UtilsShell#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
        return execCommandNormal(commands == null ? null : commands.toArray(new String[] {}), isRoot, isNeedResultMsg);
    }
    public static CommandResult execCommandCache(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
    	return execCommandCache(commands == null ? null : commands.toArray(new String[] {}), isRoot, isNeedResultMsg);
    }

    /**
     * execute shell commands
     * 
     * @param commands command array
     * @param isRoot whether need to run with root
     * @param isNeedResultMsg whether need result msg
     * @return <ul>
     * <li>if isNeedResultMsg is false, {@link CommandResult#successMsg} is null and {@link CommandResult#errorMsg} is
     * null.</li>
     * <li>if {@link CommandResult#result} is 0, success.</li>
     * <li>if {@link CommandResult#result} is other, there maybe some excepiton.</li>
     * </ul>
     */
    public static CommandResult execCommandNormal(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;

        DataOutputStream os = null;
        try {
            process = new ProcessBuilder(isRoot ? COMMAND_SU : COMMAND_SH).start(); //Runtime.getRuntime().exec(isRoot ? COMMAND_SU+" -a" : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }

                // donnot use os.writeBytes(commmand), avoid chinese charset error
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();

            result = process.waitFor();
            // get command result
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
            }
            try {
                if (successResult != null) {
                    successResult.close();
                }
            } catch (Exception e) {
            }
            try {
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (Exception e) {
            }

            if (process != null) {
                process.destroy();
            }
        }
        
        return new CommandResult(result, 
                successMsg == null ? null : successMsg.toString(), 
                errorMsg == null ? null : errorMsg.toString());
    }
    
    private static Process processCache;
    private static DataOutputStream osCache;
    private static StreamThread outStreamCache;
    private static StreamThread errorStreamCache;
    public static CommandResult execCommandCache(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
    	int result = -1;
    	if (commands == null || commands.length == 0) {
    		return new CommandResult(result, null, null);
    	}
    	
    	try {
    		if(processCache == null) {
    			processCache = new ProcessBuilder(isRoot ? COMMAND_SU : COMMAND_SH).start();
    			
    			osCache = new DataOutputStream(processCache.getOutputStream());
    			
    			outStreamCache = new StreamThread(processCache.getInputStream());  
    			outStreamCache.start();
    			
    			errorStreamCache = new StreamThread(processCache.getErrorStream());              
    			errorStreamCache.start();  
    		}
    		
    		outStreamCache.mResult = new StringBuilder();
    		errorStreamCache.mResult = new StringBuilder();
    		
    		for (String command : commands) {
    			if (command == null) {
    				continue;
    			}
    			
    			// donnot use os.writeBytes(commmand), avoid chinese charset error
    			osCache.write(command.getBytes());
    			osCache.writeBytes(COMMAND_LINE_END);
    			osCache.flush();
    		}
    	} catch (Exception e) {
            e.printStackTrace();
    	}  finally {
        }
    	
    	try {
			Thread.sleep(4000);
		} catch (Exception e) {
		}
    	
		String successMsg = outStreamCache.mResult.toString();
		String errorMsg = errorStreamCache.mResult.toString();
		
    	return new CommandResult(result, 
    			successMsg == null ? null : successMsg.toString(), 
    					errorMsg == null ? null : errorMsg.toString());
    }

    /**
     * 用于处理Runtime.getRuntime().exec产生的错误流及输出流
     *
     */
    private static class StreamThread extends Thread {
    	public StringBuilder mResult = new StringBuilder();
    	
    	private InputStream mIs;
    	    
    	public StreamThread(InputStream is) {
    		this.mIs = is;
    	}
    	
    	@Override
        public void run() {
    		InputStreamReader isr = null;
        	BufferedReader br = null;
        	
            try {
            	isr = new InputStreamReader(mIs);
                br = new BufferedReader(isr);
                
                String line=null;
                while ((line = br.readLine()) != null) { 
                	mResult.append(line);
                }  
            } catch (Exception e) {
                e.printStackTrace();  
            } finally{
            	try {
            		if(br != null) {
            			br.close();
            		}
				} catch (Exception e) {
				}
            	
            	try {
            		if(isr != null) {
            			isr.close();
            		}
            	} catch (Exception e) {
            	}
            }
        }
    } 

    /**
     * result of command,
     * <ul>
     * <li>{@link CommandResult#result} means result of command, 0 means normal, else means error, same to excute in
     * linux shell</li>
     * <li>{@link CommandResult#successMsg} means success message of command result</li>
     * <li>{@link CommandResult#errorMsg} means error message of command result</li>
     * </ul>
     * 
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-16
     */
    public static class CommandResult {

        /** result of command **/
        public int    result;
        /** success message of command result **/
        public String successMsg;
        /** error message of command result **/
        public String errorMsg;

        public CommandResult(int result){
            this.result = result;
        }

        public CommandResult(int result, String successMsg, String errorMsg){
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }
}
