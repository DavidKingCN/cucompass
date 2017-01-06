package cn.com.davidking.task;

public enum ExecCode {
	ExecSucc("0"),		//执行成功码
	ExecFail("-1"),		//执行失败码
	ReadConfErr("-2"),	//读取配置文件错误码
	MxNull("-3"),		//模型空错误码
	PagePickErr("-4"),	//分页提取错误码
	RespTxtErr("-5"),	//响应正文错误码
	CloseErr("-6");		//关闭资源错误码
	private String code;

	private ExecCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	public static void main(String[] args) {
		System.out.println(" "+ExecCode.ExecSucc.getCode());
		System.out.println(ExecCode.ExecFail.getCode());
		System.out.println(ExecCode.ReadConfErr.getCode());
		System.out.println(ExecCode.MxNull.getCode());
		System.out.println(ExecCode.PagePickErr.getCode());
		System.out.println(ExecCode.RespTxtErr.getCode());
		System.out.println(ExecCode.CloseErr.getCode());
	}
}
