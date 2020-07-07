//import com.jiwell.comments.LyCommentsApplication;
//import com.jiwell.comments.dao.SpitDao;
//import com.jiwell.comments.pojo.Spit;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = LyCommentsApplication.class)
//public class jiwellReviewServieApplicationTests {
//
//    @Autowired
//    private SpitDao spitDAO;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Test
//    public void contextLoads() {
//        Spit spit = new Spit();
//        spit.set_id("5");
//        spit.setContent("123123123");
//        spit.setNickname("123123123");
//        spit.setUserid("123121");
//        spit.setVisits(1234);
//        spitDAO.save(spit);
//    }
//
//    @Test
//    public void findTest(){
//        Criteria criteria = new Criteria();
//        criteria.andOperator(Criteria.where("_id").is("5"),Criteria.where("visits").is(1234));
//        List<Spit> spits = this.mongoTemplate.find(new Query(criteria),Spit.class);
//        System.out.println("数量："+spits.size());
//        for (Spit spit : spits){
//            System.out.println(spit);
//        }
//    }
//}