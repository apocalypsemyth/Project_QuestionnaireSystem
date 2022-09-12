package com.QuestionnaireProject.QuestionnaireSystem;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Question;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Questionnaire;
import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.repository.QuestionDao;
import com.QuestionnaireProject.QuestionnaireSystem.repository.QuestionnaireDao;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.QuestionService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserAnswerService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserService;
import com.QuestionnaireProject.QuestionnaireSystem.util.DateTimeUtil;

@WebAppConfiguration //要使用 web 環境模擬測試
@SpringBootTest(classes = QuestionnaireSystemApplication.class) //為找到@SpringBootApplication主配置類別來啟動Spring Boot應用程式環境
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //為了可以使用@BeforeAll 和 @AfterAll
//@TestPropertySource("classpath:application-test.properties")
class QuestionnaireSystemApplicationTests {
	
	//mockMvc 是基於 webApplicationContext 所建立的物件，可以用來編寫 web 應用的整合測試
//	@Autowired
//	private WebApplicationContext wac;
	
	//實現對 http 請求的模擬，主要是用來測試 controller
//	private MockMvc mockMvc;
	
//	@BeforeAll
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//    }
	
	@Autowired
	private QuestionnaireDao questionnaireDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAnswerService userAnswerService;
	
//	@BeforeEach
	public void personBeforeEach() {
		// save
//		Person item = new Person();
//		item.setName("C01");
//		item.setEmail("c01@gmail.com");
//		item.setMobile("0912345678");
//		item.setAge("20");
//		item.setCreateTime(new Date());
//		personDao.save(item);
	}
	
	// May doesn't exist
	public static final String QUESTIONNAIRE_ID = "461b8512-5738-4b7a-b0f8-7c6e917893f1";
	
	@Test
	public void testGetAnswerNumStatMap() throws Exception {
		List<Question> questionList = 
				questionService.getQuestionList(QUESTIONNAIRE_ID, true);
		List<UserAnswer> userAnswerList = 
				userAnswerService.getUserAnswerList(QUESTIONNAIRE_ID);
		for (var question : questionList) {
			UUID questionId = question.getQuestionId();
			List<UserAnswer> questionItsUserAnswerList =
					userAnswerList
					.stream()
					.filter(item -> item.getQuestionId().equals(questionId))
					.collect(Collectors.toList());
			Map<Integer, Double> answerNumStatMap = 
					questionItsUserAnswerList
					.stream()
					.collect(
							Collectors.groupingBy(UserAnswer::getAnswerNum
									, Collectors.counting())
							)
					.entrySet()
					.stream()
					.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().doubleValue()));
			int answerNumTotal = 
					answerNumStatMap.values().stream().reduce(0.0, Double::sum).intValue();
			System.out.println("answerNumTotal: " + answerNumTotal);
			System.out.println(
					question.getQuestionName() 
					+ " " 
					+ question.getQuestionTyping() 
					+ " " 
					+ answerNumStatMap
					);
			
			String questionAnswerRaw = question.getQuestionAnswer();
			String[] questionAnswerList = questionAnswerRaw.split(";");
			for (int idx = 0; idx < questionAnswerList.length; idx++) {
				int idxPlus1 = idx + 1;
				Double prepareEachUserAnswerPercentage = 
						answerNumStatMap.get(idxPlus1);
				String eachUserAnswerPercentage = 
						prepareEachUserAnswerPercentage == null 
						? "0%" 
						: String.valueOf(Math.round(
								prepareEachUserAnswerPercentage 
								/ answerNumTotal 
								* 100
								)) + "%";
				String eachUserAnswerTotal = 
						prepareEachUserAnswerPercentage == null 
						? "0" 
						: String.valueOf(prepareEachUserAnswerPercentage.intValue());
				System.out.println(eachUserAnswerPercentage);
				System.out.println(eachUserAnswerTotal);
			}
		}
	}
	
	@Test
	public void getQuestionnaireListForPager() {
		int pageSize = 4;
		int pageIndex = 1;
		List<Questionnaire> questionnaireList = questionnaireDao.findAll();
//		questionnaireList = questionnaireList.subList(3, 14);
//		questionnaireList.forEach(item -> {
//			System.out.println(item.getCaption());
//		});
		questionnaireList.sort(Comparator.comparing(Questionnaire::getUpdateDate).reversed());
		if (questionnaireList == null || questionnaireList.isEmpty()) {
			System.out.println("Questionnaire list is null");
		}
		else if (questionnaireList.size() < pageSize) {
			questionnaireList.forEach(item -> {
				System.out.println(item.getCaption());
			});
		}
		else {
			questionnaireList = questionnaireList.subList(pageIndex * pageSize, pageIndex * pageSize + pageSize);
			questionnaireList.forEach(item -> {
				System.out.println(item.getCaption());
			});
		}
	}
	
	@Test
	public void getUserListForPager() throws Exception {
		int pageSize = 4;
		int pageIndex = 0;
		List<User> userList = userService.getUserList(QUESTIONNAIRE_ID);
		if (userList == null || userList.isEmpty()) {
			System.out.println("User list is null");
		}
		else if (userList.size() < pageSize) {
			userList.forEach(item -> {
				System.out.println(item.getUserId());
			});
		}
		else {
			userList = userList.subList(pageIndex * pageSize, pageIndex * pageSize + pageSize - 1);
			userList.forEach(item -> {
				System.out.println(item.getUserId());
			});
		}
	}
	
	@Test
	public void getQuestionList() throws Exception {
		UUID questionnaireId = UUID.fromString(QUESTIONNAIRE_ID);
		List<Question> questionList = questionDao.findByQuestionnaireId(questionnaireId);
		if (questionList == null) System.out.println("Question list is null");
		questionList.forEach(item -> {
			System.out.println(item.getQuestionId());
		});
	}
	
	@Test
	public void testLocalDateTime() throws Exception {
		String dateStr = "2022/02/02";
		System.out.println(DateTimeUtil.Convert.convertStringToLocalDateTime(dateStr));
	
		System.out.println(DateTimeUtil.Func.getLocalDateTime(true));
		System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
		
		LocalDateTime testDate = LocalDateTime.of(2022, 9, 2, 12, 00);
		LocalDateTime nowDate = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
		System.out.println(testDate.isAfter(nowDate));
	}
	
}
