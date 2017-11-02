package edu.hdu.controller;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class PySpider {

    private String HOST = "127.0.0.1";
    private int SERVER_PORT = 5000;
    private String project;

    private String start_url = "XXX";
    private String key_word = "XXX";
    private String next_page = "XXX";
    private int page_num = 0;
    private String item_select = "XXX";
    private ArrayList<String> filter_word = new ArrayList<String>(){{add("XXX");}};
    private String publish_time_select = "XXX";
    private String template =
            "from pyspider.libs.base_handler import *\n" +
            "import sys,re,time,pymysql\n" +
            "reload(sys)\n" +
            "sys.setdefaultencoding('utf8')\n" +
            "    \n" +
            "\n" +
            "class Handler(BaseHandler):\n" +
            "    crawl_config = {\n" +
            "    }\n" +
            "\n" +
            "    @every(minutes=24 * 60)\n" +
            "    def on_start(self):\n" +
            "        self.crawl(start_url, fetch_type = 'js', callback=self.index_page)\n" +
            "    \n" +
            "    def get_url(self,response,keyword):\n" +
            "        body = response.doc('body')\n" +
            "        body_str = str(body.html().decode('utf-8'))\n" +
            "        loc = body_str.find(keyword)+len(keyword)/2\n" +
            "        url_dict = dict()\n" +
            "        distance_list = []\n" +
            "        beg = 0\n" +
            "        for each in body('a').items():\n" +
            "            s = str(each.attr.href)\n" +
            "            loc_i = body_str.find(s,beg)\n" +
            "            d = abs((loc_i+len(s)/2)-loc)\n" +
            "            beg = loc_i+len(s)\n" +
            "            url_dict.setdefault(d,s)\n" +
            "            distance_list.append(d)\n" +
            "        key_url = url_dict.get(min(distance_list))\n" +
            "        return key_url\n" +
            "\n" +
            "    @config(age=5 * 24 * 60 * 60)\n" +
            "    def index_page(self, response):\n" +
            "        self.crawl(self.get_url(response,key_word), fetch_type = 'js', callback=self.detail_page)\n" +
            "\n" +
            "    @config(age=10 * 24 * 60 * 60)\n" +
            "    def detail_page(self, response):\n" +
            "        key_url = self.get_url(response,next_page)\n" +
            "        key_url_arr = key_url.split('/')\n" +
            "        key_url_tail = key_url_arr[len(key_url_arr)-1]\n" +
            "        key_url_tail_tmp = ''\n" +
            "        for c in re.findall(r'[^0-9]',key_url_tail):\n" +
            "            key_url_tail_tmp+=c\n" +
            "        key_url_list = key_url_tail_tmp.split('.')\n" +
            "        tmp = ''\n" +
            "        for i in range(0,len(key_url_arr)-1):\n" +
            "            tmp += key_url_arr[i]+'/'\n" +
            "        key_url_list[0]=tmp+key_url_list[0]\n" +
            "        key_url_list[1]='.'+key_url_list[1]\n" +
            "        for i in range(1,page_num+1):\n" +
            "            url = key_url_list[0]+str(i)+key_url_list[1]\n" +
            "            self.crawl(url,fetch_type = 'js', callback=self.detail_page2)\n" +
            "            \n" +
            "    @config(priority=6)\n" +
            "    def detail_page2(self, response):\n" +
            "        for each in response.doc(item_select).items():\n" +
            "            title = str(each.text().decode('utf-8'))\n" +
            "            for word in filter_word:\n" +
            "                if title.find(word) != -1:\n" +
            "                    self.crawl(each.attr.href,fetch_type = 'js',callback=self.detail_page3)\n" +
            "                    break;\n" +
            "            \n" +
            "    @config(priority=5)\n" +
            "    def detail_page3(self, response):\n" +
            "        url = response.url\n" +
            "        title=response.doc('title').text()\n" +
            "        content=''\n" +
            "        for each in response.doc('p').items():\n" +
            "            content+=each.text()\n" +
            "        crawl_time = time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))\n" +
            "        publish_time = response.doc(publish_time_select).text()\n" +
            "        conn= pymysql.connect(host='127.0.0.1',port=3306,user='root',passwd='sdn',db='repository',charset='utf8')\n" +
            "        cur = conn.cursor()\n" +
            "        cur.execute(\"insert into shuiliting(title,url,context,crawl_time,publish_time) values(%s,%s,%s,%s,%s)\",(title,url,content,crawl_time,publish_time))\n" +
            "        conn.commit()\n" +
            "        cur.close()\n" +
            "        conn.close()";

    private String generateScript(){
        String script = "start_url='"+this.start_url+"'\nnext_page='"+this.next_page+"'\n" +
                "page_num="+this.page_num+"\nitem_select='"+this.item_select+"'\n" +
                "publish_time_select='"+this.publish_time_select+"'\nfilter_word=[";
        for(String word:this.filter_word){
            script+="'"+word+"',";
        }
        script+="]\n\n";
        script = script.replace(",]","]");
        script+=this.template;
        return script;
    }

    private String END = "\r\n";
    private Charset CHARSET = new Charset();
    private Action ACTION = new Action();
    private Status STATUS = new Status();

    class Charset{
        public String UTF_8 = "UTF-8";
    }
    class Action{
        public String POST = "POST";
        public String GET = "GET";
    }

    class Status{
        public String TODO = "TODO";
        public String STOP = "STOP";
        public String CHECKING = "CHECKING";
        public String DEBUG = "DEBUG";
        public String RUNNING = "RUNNING";
    }
    class Format{
        public String JSON = "json";
        public String TXT = "txt";
        public String CSV = "csv";
    }

    /**
     * 使用socket进行post或get操作
     * @param action
     * @param path
     * @param data
     * @return
     * @throws IOException
     */
    private String doAction(String action,String path,String data) throws IOException{
        // 建立连接
        InetAddress addr = InetAddress.getByName(this.HOST);
        Socket socket = new Socket(addr, this.SERVER_PORT);
        // 发送数据头和数据头
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),this.CHARSET.UTF_8));
        if(action.equals(this.ACTION.POST)) {
            String packet = String.format("%s %s HTTP/1.0%s"
                            + "HOST:%s%s"
                            + "Content-Length:%d%s"
                            + "Content-Type:application/x-www-form-urlencoded%s%s"
                            + "%s%s",
                    action,path,END,
                    this.HOST,END,
                    data.length(),END,
                    END,END,
                    data,END);
            wr.write(packet);
        }else if(action.equals(this.ACTION.GET)) {
            String packet = String.format("%s %s HTTP/1.0%s"
                            + "HOST:%s%s"
                            + "%s",action,path,END,
                    this.HOST,END,
                    END);
            wr.write(packet);
        }
        wr.flush();
        // 读取返回信息
        BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream(),this.CHARSET.UTF_8));
        String result = new String();
        String line = null;
        while ((line = rd.readLine()) != null){
            result+=line+"\n";
        }
        wr.close();
        rd.close();
        //去除socket返回数据包的头部信息
        result = result.substring(result.indexOf("\n\n")+2, result.length());
        return result.substring(0,result.length()-1);
    }

    /**
     * 保存python脚本到服务器
     * 返回字符串的格式:string
     * @param project
     * @param script
     * @return
     * @throws IOException
     */
    private String saveScript(String project,String script)throws IOException{
        return this.doAction(this.ACTION.POST,String.format("/debug/%s/save", project),
                "script="+ URLEncoder.encode(script,this.CHARSET.UTF_8));
    }

    /**
     * 改变项目的运行的状态
     * 返回字符串的格式:string
     * @param project
     * @param status
     * @return
     * @throws IOException
     */
    private String changeProjectStatus(String project,String status)throws IOException{
        String data = "pk=" + URLEncoder.encode(project,this.CHARSET.UTF_8) +
                "&name=" + URLEncoder.encode("status",this.CHARSET.UTF_8) +
                "&value=" + URLEncoder.encode(status,this.CHARSET.UTF_8);
        return this.doAction(this.ACTION.POST,"/update",data);
    }

    /**
     * 运行爬虫项目
     * @return
     * @throws IOException
     */
    private String runProject() throws IOException{
        return this.doAction(this.ACTION.POST,"/run","project="+URLEncoder.encode(this.project,this.CHARSET.UTF_8));
    }

    public String createProject(String project,String start_url,
                                String key_word,String next_page,
                                int page_num,String item_select,
                                ArrayList<String> filter_word,
                                String publish_time_select)throws IOException{
        this.project = project;
        this.start_url = start_url;
        this.key_word = key_word;
        this.next_page = next_page;
        this.page_num = page_num;
        this.item_select = item_select;
        this.filter_word = filter_word;
        this.publish_time_select = publish_time_select;

        return this.saveScript(project,this.generateScript());
    }

    /**
     * 开启一个项目
     * @return
     * @throws IOException
     * @throws AWTException
     */
    public String startProject()throws IOException,AWTException{
        this.changeProjectStatus(this.project,this.STATUS.RUNNING);
        Robot r = new Robot();//执行完上面一条必须等待改变项目的运行状态为运行，才可以正式运行项目
        r.delay(100);//ms
        return this.runProject();
    }

    /**
     * 停止一个项目
     * @return
     * @throws IOException
     */
    public String stopProject()throws IOException{
        return this.changeProjectStatus(this.project,this.STATUS.STOP);
    }


    public static void main(String[] args){
        PySpider p = new PySpider();
        System.out.println(p.generateScript());
    }



}
