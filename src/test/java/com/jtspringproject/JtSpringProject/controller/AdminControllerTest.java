package com.jtspringproject.JtSpringProject.controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.services.categoryService;
import com.jtspringproject.JtSpringProject.services.productService;
import com.jtspringproject.JtSpringProject.services.userService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

@ContextConfiguration(classes = {AdminController.class})
@ExtendWith(SpringExtension.class)
class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @MockBean
    private categoryService categoryService;

    @MockBean
    private productService productService;

    @MockBean
    private userService userService;

    /**
     {@link AdminController#updateCategory(int, String)}
     */
    @Test
    void testUpdateCategory() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        when(categoryService.updateCategory(anyInt(), Mockito.<String>any())).thenReturn(category);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/categories/update");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("categoryid", String.valueOf(1))
                .param("categoryname", "foo");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/categories"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
    }

    /**
     {@link AdminController#addProduct()}
     */
    @Test
    void testAddProduct() throws Exception {
        when(categoryService.getCategories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/products/add");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories"))
                .andExpect(MockMvcResultMatchers.view().name("productsAdd"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("productsAdd"));
    }

    /**
     {@link AdminController#addProduct()}
     */
    @Test
    void testAddProduct2() throws Exception {
        when(categoryService.getCategories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/products/add");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories"))
                .andExpect(MockMvcResultMatchers.view().name("productsAdd"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("productsAdd"));
    }

    @Test
    void testAdminHome() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/Dashboard");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/login"));
    }

    /**
     {@link AdminController#adminlogin()}
     */
    @Test
    void testAdminlogin() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/login");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("adminlogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("adminlogin"));
    }

    /**
     {@link AdminController#adminlogin()}
     */
    @Test
    void testAdminlogin2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/login");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("adminlogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("adminlogin"));
    }

    /**
     {@link AdminController#getCustomerDetail()}
     */
    @Test
    void testGetCustomerDetail() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/customers");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("adminlogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("adminlogin"));
    }

    /**
     {@link AdminController#getcategory()}
     */
    @Test
    void testGetcategory() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/categories");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("adminlogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("adminlogin"));
    }

    /**
     {@link AdminController#getproduct()}
     */
    @Test
    void testGetproduct() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/products");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("adminlogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("adminlogin"));
    }

    /**
     {@link AdminController#index(Model)}
     */
    @Test
    void testIndex() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/index");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("userLogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userLogin"));
    }

    /**
     {@link AdminController#postproduct()}
     */
    @Test
    void testPostproduct() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/products");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/categories"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
    }

    /**
     {@link AdminController#profileDisplay(Model)}
     */
    @Test
    void testProfileDisplay() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/profileDisplay");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("updateProfile"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("updateProfile"));
    }

    /**
     {@link AdminController#profileDisplay(Model)}
     */
    @Test
    void testProfileDisplay2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/profileDisplay");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("updateProfile"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("updateProfile"));
    }

    /**
     {@link AdminController#removeProduct(int)}
     */
    @Test
    void testRemoveProduct() throws Exception {
        when(productService.deleteProduct(anyInt())).thenReturn(true);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/products/delete");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("id", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/products"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/products"));
    }

    /**
     * {@link AdminController#updateProduct(int, String, int, int, int, int, String, String)}
     */
    @Test
    void testUpdateProduct() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/admin/products/update/{id}", 1);
        MockHttpServletRequestBuilder paramResult = postResult.param("categoryid", String.valueOf(1))
                .param("description", "foo")
                .param("name", "foo");
        MockHttpServletRequestBuilder paramResult2 = paramResult.param("price", String.valueOf(1))
                .param("productImage", "foo");
        MockHttpServletRequestBuilder paramResult3 = paramResult2.param("quantity", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult3.param("weight", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/products"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/products"));
    }

    /**
     * {@link AdminController#updateUserProfile(int, String, String, String, String)}
     */
    @Test
    void testUpdateUserProfile() throws Exception {
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.post("/admin/updateuser")
                .param("address", "foo")
                .param("email", "foo")
                .param("password", "foo");
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("userid", String.valueOf(1))
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/index"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/index"));
    }

    /**
     {@link AdminController#updateproduct(int)}
     */
    @Test
    void testUpdateproduct() throws Exception {
        when(categoryService.getCategories()).thenReturn(new ArrayList<>());

        Category category = new Category();
        category.setId(1);
        category.setName("Name");

        Product product = new Product();
        product.setCategory(category);
        product.setDescription("The characteristics of someone or something");
        product.setId(1);
        product.setImage("Image");
        product.setName("Name");
        product.setPrice(1);
        product.setQuantity(1);
        product.setWeight(3);
        when(productService.getProduct(anyInt())).thenReturn(product);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/products/update/{id}", 1);
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories", "product"))
                .andExpect(MockMvcResultMatchers.view().name("productsUpdate"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("productsUpdate"));
    }
}
