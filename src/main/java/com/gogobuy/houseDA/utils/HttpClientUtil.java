package com.gogobuy.houseDA.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/**
 * @program: tao-center
 * @description:
 * @author: YanZhan
 * @create: 2020-01-31 14:58
 **/
@Slf4j
public class HttpClientUtil {
    private static CloseableHttpClient httpclient = null;
    private static CookieStore cookieStore = null;

    static {
        cookieStore = new BasicCookieStore();
        httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    }


    public static class HttpReqParam{

        private List<Pair<String,String>> paramList = Lists.newArrayList();

        public HttpReqParam param(String key,String value){
            if (StringUtils.isEmpty(value)){
                return this;
            }
            paramList.add(Pair.of(key,value));
            return this;
        }
        public List<Pair<String, String>> get(){
            return paramList;
        }
    }


    public static String getNoCheckedException(String uri, HttpReqParam param){
        try {
            uri = buildUri(uri, param.get());
            log.info("uri:{}", uri);
            return get(uri, null);
        } catch (Exception e) {
            log.error("act=【getNoCheckedException】desc=【GET请求异常】e=【{}】",e);
            throw new cn.hutool.http.HttpException("Get请求异常");
        }
    }

    public static String getNoCheckedException(String uri, List<Header> headers, List<Pair<String, String>> params){
        try {
            if (CollectionUtils.isNotEmpty(params)){
                uri = buildUri(uri, params);
            }
            log.info("uri:{}", uri);
            return get(uri, headers);
        } catch (Exception e) {
            log.error("act=【getNoCheckedException】desc=【GET请求异常】e=【{}】",e);
            throw new cn.hutool.http.HttpException("Get请求异常");
        }
    }

    public static String get(String uri, List<Header> headers, List<Pair<String, String>> params) throws Exception {
        uri = buildUri(uri, params);
        log.info("uri:{}", uri);
        return get(uri, headers);
    }

    private static String buildUri(String uri, List<Pair<String, String>> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(uri);
        sb.append("?");
        params.forEach(new Consumer<Pair<String, String>>() {
            @Override
            public void accept(Pair<String, String> pair) {
                sb.append(pair.getKey());
                sb.append("=");
                sb.append(pair.getValue());
                sb.append("&");
            }
        });
        return sb.toString();
    }

    public static String get(String uri, List<Header> headers){
        HttpEntity entity = null;
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(uri);
            if (CollectionUtils.isNotEmpty(headers)) {
                httpGet.setHeaders(headers.toArray(new Header[headers.size()]));
            }
            response = httpclient.execute(httpGet);
            entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            log.error("http get error:", e);
            throw new RuntimeException(e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取cookie的值
     *
     * @param domain
     * @param name
     * @return
     */
    public static String getCookie(String domain, String name) {
        List<Cookie> cookies = cookieStore.getCookies();
        Cookie cookie = cookies.stream()
                .filter(c -> c.getDomain().equalsIgnoreCase(domain))
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(null);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    public static String post(String uri, List<Header> headers, List<NameValuePair> formparams){
        HttpEntity entity = null;
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(uri);

            if (CollectionUtils.isNotEmpty(headers)) {
                httpPost.setHeaders(headers.toArray(new Header[headers.size()]));
            }


            if (CollectionUtils.isNotEmpty(formparams)) {
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
                httpPost.setEntity(formEntity);
            }

            response = httpclient.execute(httpPost);
            entity = response.getEntity();
            return EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            log.error("http get error:", e);
            throw new RuntimeException(e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        String s = get("http://www.baidu.com", null);
//        System.out.println(s);


        // 首页访问 获取Cookie2
        String index = get("https://pub.alimama.com/", null);


        // 登录
        List<Header> loginHeader = Lists.newArrayList();
        loginHeader.add(new BasicHeader("Referer", "https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&from=alimama&redirectURL=http://login.taobao.com/member/taobaoke/login.htm?is_login=1&full_redirect=true&disableQuickLogin=true"));
        loginHeader.add(new BasicHeader("Cookie", "_uab_collina=156128102474494417841543; thw=cn; t=82af2b44318b1c90249e705ffbc63dab; cna=794YFRO IAACASvkJMJy1dqw; hng=GLOBAL|zh-CN|USD|999; miid=902157201569825994; tracknick=tb391539_55; lid=tb391539_55; tg=0; enc=KCIVCAxC7B6BydVFukxCBoQSXVrRhvO5XN2m/7LNjTFO9dRVy8dpVop9hU3c xCZL LJjIRPME8tinY5aOIFLA==; x=e=1&p=*&s=0&c=0&f=0&g=0&t=0; log=lty=Ug==; lgc=tb391539_55; munb=1109317476; mt=ci=50_1; UM_distinctid=16fe768b5c6b5-0f664610dd2d8e-c343162-144000-16fe768b5c852; _samesite_flag_=true; cookie2=1c0b8997052375dfc24236521ee22bea; _tb_token_=5ba31f347668e; uc3=lg2=WqG3DMC9VAQiUQ==&nk2=F5RGP7AP6tle O4=&vt3=F8dBxdsSHe7aL0EL2j8=&id2=UoCJgTmrYupa/w==; csg=747472ff; dnk=tb391539_55; skt=4b6612e3573e51e6; existShop=MTU4MDQ4Mjk0NA==; uc4=nk4=0@FY4NCuUCaSk23ue7cUOz8YMN ryaRQ==&id4=0@UOg1yYJDgtptZsZEVcK4zro8MLl/; lc=Vyu53UPeryI5qmd/u/Ap6A==; _cc_=WqG3DMC9EA==; uc1=cookie14=UoTUOqS0BX/g/Q==&cookie21=WqG3DMC9Fb5mPLIQo9kR&pas=0&tag=8&existShop=false&cookie16=V32FPkk/xXMk5UvIbNtImtMfJQ==&lng=zh_CN; isg=BBAQzpmWeqcTfyaj-6iMl2hO4V5i2fQjBk4PvArh_ms-RbLvs-lus_ozHQ2lqKz7; l=cBEaQgjlQ_SlHP_kBOCgVuI81qQ9dIRqSuPRwGWpi_5I86L1zA7Oo4KbKFp6cjWFGLYp4k6UXwpTFeUaJyTb2Bt3CaZTWaf.."));

        List<NameValuePair> loginParams = Lists.newArrayList();
        loginParams.add(new BasicNameValuePair("TPL_username", "tb391539_55"));
        loginParams.add(new BasicNameValuePair("ncoToken", "b7c71cad64fb0b6086493fecfb656d36febcc4cf"));
        loginParams.add(new BasicNameValuePair("slideCodeShow", "false"));
        loginParams.add(new BasicNameValuePair("useMobile", "false"));
        loginParams.add(new BasicNameValuePair("lang", "zh_CN"));
        loginParams.add(new BasicNameValuePair("TPL_redirect_url", "http://login.taobao.com/member/taobaoke/login.htm?is_login=1"));
        loginParams.add(new BasicNameValuePair("from", "alimama"));
        loginParams.add(new BasicNameValuePair("fc", "default"));
        loginParams.add(new BasicNameValuePair("style", "mini"));
        loginParams.add(new BasicNameValuePair("keyLogin", "false"));
        loginParams.add(new BasicNameValuePair("qrLogin", "true"));
        loginParams.add(new BasicNameValuePair("newMini", "false"));
        loginParams.add(new BasicNameValuePair("loginType", "3"));
        loginParams.add(new BasicNameValuePair("full_redirect", "true"));
        loginParams.add(new BasicNameValuePair("gvfdcname", "10"));
        loginParams.add(new BasicNameValuePair("TPL_password_2", "1e529c32e43ee4f697b381c50a4b3a7b128ba35ad502dfa619db1ef2637cdb64c13f8a24a6431773f49f5f79e6ff7d174f251fc19598d639722f045b43640aafe82f4734bea90f3f61a85ae4e0f4d89f5e5a6e030dae2885b1eb3700d8dac4dc746c9db061b80da954790f6d020c21b16bebc41fe9019b35aff558395f970e79"));
        loginParams.add(new BasicNameValuePair("loginASR", "1"));
        loginParams.add(new BasicNameValuePair("loginASRSuc", "1"));
        loginParams.add(new BasicNameValuePair("osAV", "5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36"));
        loginParams.add(new BasicNameValuePair("um_token", "T1430DDD17FBF7E86DB6399D600285F45731364F75AADA67C72D5EE0FF8"));
        loginParams.add(new BasicNameValuePair("ua", "122#F6DT04j/EE+Tq4pZMEpaEJponDJE7SNEEP7rEJ+/XMVa0CQLpo7iEDpWnDEbK51HpyGZp9hBuDEEJFOPpC76EJponDJL7gNpEPXZpJRgu4Ep+FQLpoGUEELWn4yP7SQEEyuLpEyrLnVpprARXON5vE7K9ExzlOmnRP5XE8AR/kzKE/fSBKitEfBDePO7UX4CGh5fvVN+z09UoQWfKYa/08VvIl1DqMfEdh7+K7LcuOIEEu/y8NseJ8bbyFfmqMf2ENpCVJ0koP0EDLXT8BLUJNI2UV4n7W3bD93xnSL1elWEELXZ8oL6JNEEyBfDqMAbEEpangL4ul0EDLVr8CpUJ4bEyF3mqWivDEpxqMh4GljEEWfcujvUtfMx3EiDnMInpu4GB4ftM0+L3ria3Gg0QSasrKHsmcJpPEfwljkiZzCsQidimmki2jqPKM8VgRxuwNQTTfVk0bf0vhqAcFz0deXE+NYaVnvFbuq/zJcdAWbmt1tejSgl//+dyq82IqttVb/3RFRvqzB2QjWL7xlfrkyXAm4zQL/nmE8ihd0s1Jw9QV3+x7A1TcRXFtixDAPQGHUsQiIjKA/ICDix+8A1w+8O3Mt/M8BjVTo7emTpd4XSRQ3pHkgzly7woyoaXWS3RLTMXkOapx9EApFEW9Iqae6c7mRfMDufHur7QOV2WDLtoiNkyXKgsQRMypDEKnY2BHpchbPh7HdJeASqnmiBzHeXMNIzRI2C6ktvdnQkPIbU3qi+G/eL9Czu/My9d8azeavbxPirefRl8r0g7Q/xY01HT3YsJMrCe9uWxClq2sU5RQcMCFcyXO0lrv770bW9+FXq6RUBSVPbvM8QdXuKBEHV16K0xzYNbJBckr4uy632tfwb6MzVvjepBRtGS83YS0Q6E9KJ/y9evywJTwMMmU6XIp7oDeg8PxgHaggBZLSOwC3m1quQYXqCiNOo19i4mBS9XfSt3KMR2J96JqrAYvmI88BDKVLwK5IaQb9djszbTGoVKz8gm8KnLr3hSmLR8cBLSQeX7h1cuhlo8SSK0tHciDLJpmctYpJyAtsBTstXmizbTff7J4CkMpOtMUYoD1UuZpqybWwhbDdBhYwjtcgBjZ4hjGxu4mo3SNq5hcsPOgJ9wA7YpCOzKfTxjZWIqdCOFuwD6oXz4KbFhKLpTPFnul3w2YZwXblK8B7tBmT+ZdHuoDZniqvHdXD9+GG4Mly6qkrZ5utUazDDqGg1JM+eVm3c7NC4j88gz8/5FNvRdcPXkDVLu6R/zqihYa5="));
        String loginResponse = post("https://login.taobao.com/member/login.jhtml?redirectURL=http://login.taobao.com/member/taobaoke/login.htm?is_login=1", loginHeader, loginParams);

        String redirectUrl = getRedirectrl(loginResponse);
        log.info("redirectUrl : {}", redirectUrl);

        // 登录跳转
        List<Header> headers = Lists.newArrayList();
        headers.add(new BasicHeader("Referer", "https://login.taobao.com/member/login.jhtml?redirectURL=http://login.taobao.com/member/taobaoke/login.htm?is_login=1"));
        String s1 = get(redirectUrl, headers);
        System.out.println(s1);

        // 访问实时销量接口
        List<NameValuePair> nameValuePairs = Lists.newArrayList();
        nameValuePairs.add(new BasicNameValuePair("_data_", "{\"floorId\":\"27394\",\"pageNum\":0,\"pageSize\":60,\"refpid\":\"mm_127972942_0_0\"}"));

        List<Header> headerList = Lists.newArrayList();
//        headerList.add(new BasicHeader("Cookie","account-path-guide-s1=true;cookie2=1d40ecfa1796e989088c99e551191bcb;"));
        String post = post("https://pub.alimama.com/openapi/json2/1/gateway.unionpub/optimus.material.json?t=1580482947679&_tb_token_=70ebe84ed54eb", headerList, nameValuePairs);
        System.out.println(post);

    }

    private static String getRedirectrl(String loginResponse) {
        return StrUtil.subBetween(loginResponse, "top.location.href = \"", "\";");
    }

}
