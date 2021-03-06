package com.taiji.eap.biz.zxjg.spider;

import com.taiji.eap.biz.jcdxx.bean.Jcdxx;
import com.taiji.eap.biz.jcxm.service.JcxmService;
import com.taiji.eap.biz.jcxm.service.impl.JcxmServiceImpl;
import com.taiji.eap.biz.jcxmjg.service.JcxmjgService;
import com.taiji.eap.common.utils.SpringContextUtil;
import javafx.beans.value.ObservableObjectValue;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Set;

/**
 * @author 潘宏智
 * @date
 */
public abstract class BaseProcessor implements PageProcessor{

    protected WebDriver chromeDriver;

    protected Site site;

    protected Set<Cookie> cookies;

    protected List<Jcdxx> jcdxxes;

    protected String startDate;

    protected String endDate;

    protected JcxmService jcxmService;

    public BaseProcessor(List<Jcdxx> jcdxxes,String startDate,String endDate) {
        this.jcdxxes = jcdxxes;
        chromeDriver = new ChromeDriver();
        site = Site.me().setRetryTimes(3).setSleepTime(0).setTimeOut(3000);
        this.startDate = startDate;
        this.endDate = endDate;
        jcxmService = (JcxmService) SpringContextUtil.getBean("JcxmService");
    }

    @Override
    public Site getSite() {
        for (Cookie cookie: cookies) {
            site.addCookie(cookie.getName().toString(),cookie.getValue().toString());
        }
        return site.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1");
    }

    public WebDriver getDriver() {
        return chromeDriver;
    }

    public void setCookies(Set<Cookie> cookies) {
        this.cookies = cookies;
    }

    @Override
    public void process(Page page) {
        doProcess(page);
    }

    protected abstract void doProcess(Page page);
}
