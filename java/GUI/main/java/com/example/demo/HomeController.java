package com.example.demo;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//デファルト画面アドレス
@Controller
@RequestMapping("/")
public class HomeController {
    
    @RequestMapping(value="/index",method=RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("HomeController/index");
    }
    
    @RequestMapping(value="/",method=RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("HomeController/login");
    }
 
    @RequestMapping(value="/menu2",method=RequestMethod.GET)
    public ModelAndView menu2() {
        return new ModelAndView("HomeController/menu2");
    }
    
    @RequestMapping(value="/menu3",method=RequestMethod.GET)
    public ModelAndView menu3() {
        return new ModelAndView("HomeController/menu3");
    }
    
    @RequestMapping(value="/inslist",method=RequestMethod.GET)
    public ModelAndView inslist() {
        return new ModelAndView("HomeController/inslist");
    }
    
    @RequestMapping(value="/insadd",method=RequestMethod.GET)
    public ModelAndView insadd() {
        return new ModelAndView("HomeController/insadd");
    }
    
    @RequestMapping(value="/serverlist",method=RequestMethod.GET)
    public ModelAndView staytime() {
        return new ModelAndView("HomeController/serverlist");
    }
    
}