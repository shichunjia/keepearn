
import com.keeplearn.shichunjia.SpringbootApplication;
import com.keeplearn.shichunjia.dao.GoodsRepository;
import com.keeplearn.shichunjia.dao.SupplierRepository;
import com.keeplearn.shichunjia.pojo.Goods;
import com.keeplearn.shichunjia.pojo.Supplier;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class ElasticSearchTest {

    @Resource
    private SupplierRepository supplierRepository;
    @Resource
    private GoodsRepository goodsRepository;

    @Test
    public void saveES(){
        Supplier supplier=new Supplier();
        supplier.setName("zhansan");
        supplier.setPhone("18845784541");
        supplier.setCreate_date(new Date());
        Supplier supplier2=new Supplier();
        supplier2.setName("lisi");
        supplier2.setPhone("1889562365");
        supplier2.setCreate_date(new Date());
        supplierRepository.save(supplier);
        List<Supplier> list=new ArrayList<Supplier>() ;
        list.add(supplier);
        list.add(supplier2);
        supplierRepository.saveAll(list);
        return ;
    }

    @Test
    public void getDataFormJD() throws Exception {
        List<Goods> list=new ArrayList<Goods>() ;
        String keyWorld="手机";
        String url="https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&page=1&s=1&click=0";
        Document document=Jsoup.parse(new URL(url),10000);
        Element j_goodsList = document.getElementById("J_goodsList");
        Elements goods = j_goodsList.getElementsByTag("li");
        for(Element element : goods){
            Goods temp=new Goods();
            temp.setImg(element.getElementsByTag("img").eq(0).attr("data-lazy-img"));
            temp.setPrice(element.getElementsByClass("p-price").eq(0).text());
            temp.setTitle(element.getElementsByClass("p-name").eq(0).text());
            temp.setStore(element.getElementsByClass("p-shop").eq(0).text());
            temp.setCreateDate(new Date());
            System.out.println(temp.toString());
            list.add(temp);
        }
        goodsRepository.saveAll(list);
        return ;
    }



}
