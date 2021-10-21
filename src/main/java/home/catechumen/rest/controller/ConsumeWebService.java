package home.catechumen.rest.controller;

import home.catechumen.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@RestController
public class ConsumeWebService {

    @Autowired
    RestTemplate restTemplate;
    private final String URL = "http://91.241.64.178:7081/api/users";
    private String JSessionId;

    @RequestMapping(value = URL, method = RequestMethod.GET)
    public String getUserList() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        HttpHeaders hd = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class).getHeaders();
        String userList = restTemplate.exchange(URL, HttpMethod.GET, entity,
                String.class).getBody();
        Map<String, String> headersMap = hd.toSingleValueMap();
//        headersMap.forEach((s,k)-> System.out.println(s +" : " +  k));
        JSessionId = headersMap.get("Set-Cookie");
//        System.out.println(s);
        return userList;
    }

    @RequestMapping(value = URL, method = RequestMethod.POST)
    public String addUser(@RequestBody User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", JSessionId);
        System.out.println(httpHeaders.toSingleValueMap());
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity httpEntity = new HttpEntity<>(user, httpHeaders);
        return restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class).getBody();
    }

    @RequestMapping(value = URL, method = RequestMethod.PUT)
    public String updateUser( @RequestBody User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", JSessionId);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<User> httpEntity = new HttpEntity<>(user,httpHeaders);
        return restTemplate.exchange(URL,HttpMethod.PUT, httpEntity, String.class).getBody();
    }

    @RequestMapping(value = URL+"/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") String id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", JSessionId);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<User> httpEntity = new HttpEntity<User>(httpHeaders);
        return restTemplate.exchange(URL+ "/" + id,HttpMethod.DELETE, httpEntity, String.class).getBody();

    }
}
