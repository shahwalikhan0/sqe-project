 package com.jtspringproject.JtSpringProject.controller;
 import com.jtspringproject.JtSpringProject.models.User;
 import com.jtspringproject.JtSpringProject.services.productService;
 import com.jtspringproject.JtSpringProject.services.userService;
 import org.junit.jupiter.api.BeforeAll;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.mockito.InjectMocks;
 import org.mockito.Mockito;
 import org.openqa.selenium.chrome.ChromeDriver;
 import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
 import org.springframework.boot.test.mock.mockito.MockBean;
 import org.springframework.ui.Model;
 import org.springframework.web.servlet.ModelAndView;

 import javax.servlet.http.HttpServletResponse;

 import java.util.ArrayList;
 import java.util.List;

 import static org.junit.jupiter.api.Assertions.*;
 import static org.mockito.Mockito.when;

 @WebMvcTest(UserController.class)
 class UserControllerTest {
     @InjectMocks
     private UserController userController;

     @MockBean
     private userService userService;

     @MockBean
     private productService productService;

//     @BeforeAll
//     public static void setUp() {
//         // Use WebDriverManager to setup ChromeDriver
//         WebDriverManager.chromedriver().setup();
//
//         // Initialize WebDriver
//         ChromeDriver driver = new ChromeDriver();
//         driver.get("http://localhost:8080/");
//         System.out.println("Title: " + driver.getTitle());
//
//
//         driver.quit();
//     }

     @BeforeEach
     void setUp() {
         userService = Mockito.mock(userService.class);
         productService = Mockito.mock(productService.class);
         userController = new UserController(userService, productService);
     }

     @Test
     void testUserLoginWithAdminCredentials() {
         String username = "admin";
         String password = "123";
         User mockedAdmin = new User();
         mockedAdmin.setUsername(username);
         mockedAdmin.setPassword(password);
         mockedAdmin.setRole("ROLE_ADMIN");
         when(userService.checkLogin(username, password)).thenReturn(mockedAdmin);

         Model model = Mockito.mock(Model.class);
         HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

         ModelAndView modelAndView = userController.userlogin(username, password, model, response);

         assertEquals("userLogin", modelAndView.getViewName());
         assertTrue(modelAndView.getModel().containsKey("message"));
         assertEquals("Admin can't login from here", modelAndView.getModel().get("message"));
         assertNull(modelAndView.getModel().get("user"));
     }


     @Test
     void testUserLoginWithUserCredentials() {
         String username = "lisa";
         String password = "765";
         User mockedUser = new User();
         mockedUser.setUsername(username);
         mockedUser.setPassword(password);
         mockedUser.setRole("ROLE_USER");
         when(userService.checkLogin(username, password)).thenReturn(mockedUser);

         Model model = Mockito.mock(Model.class);
         HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

         ModelAndView modelAndView = userController.userlogin(username, password, model, response);

         assertEquals("index", modelAndView.getViewName());
         assertTrue(modelAndView.getModel().containsKey("user"));
         assertFalse(modelAndView.getModel().containsKey("message"));
     }

     @Test
     void testNewUserRegistrationWithInvalidLength() {
         User newUser = new User();
         newUser.setUsername("testUser");
         newUser.setPassword("short");
         newUser.setAddress("Test Address");

         Model model = Mockito.mock(Model.class);

         String viewName = userController.newUseRegister(newUser, model, "confirmPassword");

         assertEquals("/register", viewName);

         Mockito.verify(model, Mockito.times(1)).addAttribute("message", "Password must be at least 8 characters long");
     }

     @Test
     void testNewUserRegistrationWithValidLength() {
         User newUser = new User();
         newUser.setUsername("testUser");
         newUser.setPassword("longPassword");
         newUser.setAddress("Test Address");

         Model model = Mockito.mock(Model.class);

         String viewName = userController.newUseRegister(newUser, model, "confirmPassword");

         assertEquals("/register", viewName);

         Mockito.verify(model, Mockito.times(1)).addAttribute("message", "Password and Confirm Password must be same");
     }

     @Test
     void testUsernameShouldNotIncludeSpecialCharacters() {
         String invalidUsername = "user@name";
         boolean result = userController.containsSpecialCharacter(invalidUsername);
         assertTrue(result);
     }
     @Test
     void userNameDoesNotExists() {
         String username = "testUser";
         when(userService.getUsers()).thenReturn(new ArrayList<>());

         boolean viewName = userController.userNameExists(username);
         assertFalse(viewName);
     }

 }
