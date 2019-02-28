package com.hxy.isw.util;



/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	// 商户PID
	// public static final String PARTNER = "2088221857105591";
	public static final String PARTNER = "2088521239646448";
	// 商户收款账号
	// public static final String SELLER = "yiyitehao@qq.com";
	public static final String SELLER = "3250556087@qq.com";
	// 商户私钥，pkcs8格式
	// public static final String RSA_PRIVATE =
	// "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ/18KgAavV64vDJVbt8Vd7trzdAb2ooRVYlWNFmsTdhe2snSTktIeryR9lMpF1mKEW2iYvc3SKZSUEF8ClThzRrl/X25kNS4fKNuqm/emt3dn9YHyK470zjye8s4dwrUcBRN8yoMnaptQC/NaumbQWXYOTwlGsN2zH0ix3pbwszAgMBAAECgYAmPLIkVsU6nP92s4oM4THdsk96E6sOY0X/y7mphLTEGkdYW/HC+yjqcrP+G7YGpY7m6zWB+2/Y/29lXbgFCPR+kJ5mz55M1RvJdOcqL+tVQPikypc/bscxyg0BTfTG97umEzZ1dnsJ0cK9MJegwIk2eO2PY+prds8f5Hyth6E8gQJBAM4cwfKlGJN8gNMpwmCP+545cuW7iIksRKMe+tT35x14BAzfs2hSITjVGTZx61BCwefn0l78DKNmZ8t235FGByECQQDGrYKutPxIfPda7XmKbHXbbgsN0z6T7DPc8PhpuXETKuvWlqQy9WitfbYhLmd4jCGYds/wLkJ8MWuNPabTEMvTAkEAlloWBOn4HoZg9yjsOUJu+0HPFdSSSfY374JG2ZopRq7ozXdVyVC4GVwh8zxwunCoQII0TMOyeHPYXWzOs4uQ4QJBAI+uxCfdM+aomarku1VkgweiWdQxxbvswsUycDTlq+UU0WJrp2xDFuUBWUo96Q7BKTq+Eju8+NAgXYvT3mYea4kCQC8GOLJpICMluRZw/olef9Qi7Cfw0yy/XHEEyGpb77tnSePsEPKG0PUvT1QAtsfybAanleVfGYnav6dAMc8Xh2U=";
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJj3hDL/dCe1FAdfzFWwwj91okGTJz6uIE8gwatwbSFNSKJ6R2cdrj2e7Viaj4rMyHT9ZWlyAbthMYP7ePHr+CvW5U6QE7VUTFmLGB+bBS2GxecPgFs7Tq9cgnyDT8hklunCvXm/zQ7jceD4OSevNbQcxZWd6+v0sBF7xta6/vkbAgMBAAECgYEAlfQe/rd6MOrMpaX99AsBgaBZ3ZraIBImzis4cE4YTUsr50uj76wjAXlpBzENgm3aHQXDgqkmlYBkQBQnrlE6yIs3bTFpgXetOJ3/zzL/aMUQJkAOht9XGR1mDPd9r2EqxgCkark4p+5QLVvhm09VVsAbsz8paln8EsnZUNh/GlECQQDGZg4GTlu0xzn0eGOGjto/0DcKI4jTjUECScn5P40eEntXTe7QsbqZq/EO5tM9t7dwMaRJ6+rLE6QZwPWeZu5FAkEAxWDAb2nVlwOSelbHP/DOtHJ7jsqOb2HkePBRc0jLbhGUgfKPx+RYD9HMAE8zfHq0CyP/4n1bTnOlGuNSRwfv3wJANHBBALWxU4Tc8prnY2dalEdKZOmjfpb1Oc+bDMAZYntsElVM6+gt8/QYUOH9r3BhkcvtKoSqmGZml1ADG40FKQJAa1lf0DjDjkrHE/q2i7Qw+Dt7sAHCzhlz5cLuycDBw6+UEckXIiSzMIduaIyq9/zUh2qg9VIwos6K8cDJMxDCCQJBAIHFOPqrWlZzfvP9ZqmFq5N7SHQkzyTXk2yswvB2GOhD3tdmMbB0UIcRUR4ZfTwiFjyWtzv0ystipPcafM0w6n8=";
	// 支付宝公钥
	// public static final String RSA_PUBLIC =
	// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

	public final static String return_url = "m.alipay.com";
	public final static String it_b_pay = "30m";
	public final static String payment_type = "1";
	public final static String service = "mobile.securitypay.pay";
	public final static String subject = "Fx方程式";
	public final static String body = "Fx方程式充值支付";
}
