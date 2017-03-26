package webparser;
public class ZHAOBIAO {
	public String xmmc = "";//项目名称
	public String xmbh = "";//项目编号
	public String hylb = "";//行业类别
	public String zblb = "";//招标类别
	public String zbr = "";//招标人
	public String zbdljg = "";//招标代理机构
	public String zjly = "";//资金来源
	public String zbggbh = "";//招标公告编号
	public String zh = "";//桩号
	public String htd = "";//合同段
	public String ggfbsj = "";//公告发布时间
	public String wznr = "";//文章内容
	public String lywz = "";//来源网站
	public String url = "";//网址URL
	public String wzssdw = "";//网址所属单位
	public String html = "";//html
	public String content = "";//content
	public String title = "";

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public ZHAOBIAO copy(){
		ZHAOBIAO z = new ZHAOBIAO();
		z.xmmc =xmmc;
		z.xmbh =xmbh;
		z.hylb =hylb;
		z.zblb =zblb;
		z.zbr =zbr;
		z.zbdljg =zbdljg;
		z.zjly =zjly;
		z.zbggbh =zbggbh;
		z.zh =zh;
		z.htd =htd;
		z.ggfbsj =ggfbsj;
		z.wznr =wznr;
		z.lywz =lywz;
		z.url =url;
		z.wzssdw =wzssdw;
		z.html =html;
		z.content =content;
		z.title =title;
		return z;
	}
	
	public void display(){
		System.out.println("项目名称:"+xmmc);
		System.out.println("项目编号:"+xmbh);
		System.out.println("行业类别:"+hylb);
		System.out.println("招标类别:"+zblb);
		System.out.println("招标人:"+zbr);
		System.out.println("招标代理机构:"+zbdljg);
		System.out.println("资金来源:"+zjly);
		System.out.println("招标公告编号:"+zbggbh);
		System.out.println("桩号:"+zh);
		System.out.println("合同段:"+htd);
		System.out.println("url:"+url);
		System.out.println("------------------------------------");
	}

}


