package gduccc.command;
/**
 * 	错误码类
 * @author rober
 *
 */
public class CommandResponseCodes {
	/**
	 * 成功
	 */
    public static int Success = 0;

    /**
     * 	系统错误
     */
    public static int SystemError = -1;
    
    /**
     * 	非法请求
     */
    public static int InvalidRequest = -2;
    
    /**
     * 	非法的参数
     */
    public static int InvalidParameters = -3;
    /**
     *  session 过期
     */
    public static int SessionExpired = -4;
    /**
     * 	权限不足
     */
    public static int InsufficientPrivileges = -5;
}
