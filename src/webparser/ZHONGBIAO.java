package webparser;

public class ZHONGBIAO {
	public String xmmc = "";//项目名称
	public String xmbh = "";//项目编号
	public String hylb = "";//行业类别
	public String zblb = "";//招标类别
	public String zbr = "";//招标人
	public String zbdljg = "";//招标代理机构
	public String zjly = "";//资金来源
	public String zbbh="";//中标编号
	public String zh="";//桩号
	public String htd="";//合同段
	public String ggfbsj="";//公告发布时间
	public String dyzbhxr="";//第一中标候选人
	public String dyzbhxrbjje="";//第一中标候选人报价金额
	public String dezbhxr="";//第二中标候选人
	public String dezbhxrbjje="";//第二中标候选人报价金额
	public String dszbhxr="";//第三中标候选人
	public String dszbhxrbjje="";//第三中标候选人报价金额
	public String nzbhxr="";//拟中标候选人
	public String nzbhxrbjje="";//拟中标候选人报价金额
	public String wznr="";//文章内容
	public String lywz="";//来源网站
	public String url="";//网址url
	public String wzssdw="";//网址所属单位
	public String html = "";//html
	public String content = "";//content
	public String title = "";
	
	public static void main(String[] args) {
		
	}
	
	public ZHONGBIAO copy(){
		ZHONGBIAO z = new ZHONGBIAO();
		z.xmmc=xmmc;
		z.xmbh=xmbh;
		z.hylb=hylb;
		z.zblb=zblb;
		z.zbr=zbr;
		z.zbdljg=zbdljg;
		z.zjly=zjly;
		z.zbbh=zbbh;
		z.zh=zh;
		z.htd=htd;
		z.ggfbsj=ggfbsj;
		z.dyzbhxr=dyzbhxr;
		z.dyzbhxrbjje=dyzbhxrbjje;
		z.dezbhxr=dezbhxr;
		z.dezbhxrbjje=dezbhxrbjje;
		z.dszbhxr=dszbhxr;
		z.dszbhxrbjje=dszbhxrbjje;
		z.nzbhxr=nzbhxr;
		z.nzbhxrbjje=nzbhxrbjje;
		z.wznr=wznr;
		z.lywz=lywz;
		z.url=url;
		z.wzssdw=wzssdw;
		z.html=html;
		z.content=content;
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
		System.out.println("中标编号:"+zbbh);
		System.out.println("桩号:"+zh);
		System.out.println("合同段:"+htd);
		System.out.println("公告发布时间:"+ggfbsj);
		System.out.println("第一中标候选人:"+dyzbhxr);
		System.out.println("第一中标候选人报价金额:"+dyzbhxrbjje);
		System.out.println("第二中标候选人:"+dezbhxr);
		System.out.println("第二中标候选人报价金额:"+dezbhxrbjje);
		System.out.println("第三中标候选人:"+dszbhxr);
		System.out.println("第三中标候选人报价金额:"+dszbhxrbjje);
		System.out.println("拟中标候选人:"+nzbhxr);
		System.out.println("拟中标候选人报价金额:"+nzbhxrbjje);
		System.out.println("文章内容:"+wznr);
		System.out.println("来源网站"+lywz);
		System.out.println("网址url"+url);
		System.out.println("网址所属单位"+wzssdw);
		System.out.println("------------------------------------");
		
	}
}
