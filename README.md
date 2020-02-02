<center>

# GiTrends 1.0

</center>

### GiTrends is a RESTful API that helps get information about trending repositories from Github.

# Context :

GiTrends was developed in the context of my application for the "Data Engineer Intership" poistion at [Gemography](https://www.gemography.com/).

It was developed using [Spring Boot](https://spring.io/projects/spring-boot) and [Jsoup](https://jsoup.org/) and it is secured using [JWT](https://jwt.io/).

# Usage :

*   ## Authentication :

    The first step is to generate a JWT token that will help authenticate every request to the server. You can do so by sending a **POST** request to the following route:

    _**localhost:_8081_/api/v1/authenticate**_

    with the default user (foo ,bar) in the request body:

    _**{  
    "username":"foo",  
    "password":"bar"  
    }**_

*   ## Routes :

    After authenticating, the JWT should be added to the Authorization header after "Bearer " with every request to the endpoints below (all of them can be reached with GET requests):
    
    1.  List the languages used by the 25 trending repositories:  

        <center>localhost:8081/api/v1/repositories/trending/languages</center>

    2.  Get the number of trending repositories using a language (case insensitive path variable):  

        <center>localhost:8081/api/v1/repositories/trending/languages/{language}/count</center>

    3.  List the repositories using a language (case insensitive path variable):  

        <center>localhost:8081/api/v1/repositories/trending/languages/{language}</center>

    4.  Language popularity over the 25 trending repositories:  

        <center>localhost:8081/api/v1/repositories/trending/languages/popularity</center>
    
    You can also choose a date range (daily, monthly or weekly) within which repositories have been trending by giving the request parameter "since" a case insensitive value of "weekly", "monthly" for respectively a weekly, monthly date range. Any other value (or none at all) will set the date range to daily.
    Here is an example of requesting language popularity over the trending repositories this month: 
        
       <center>localhost:8081/api/v1/repositories/trending/languages/popularity?since=monthly</center>

    
*   ## Responses :

    Responses for valid requests take the following form :

    _**{  
    "message":"an explanatory message",  
    "value":"the value or values requested"  
    }**_

*   ## The missing features:

    I expected the Github RESTful API to expose some endpoints of trending repositories but I didn't find any. So i decided to scrape the (trending repositories)[https://github.com/trending] instead of using the API. on this web page, no information is provided about frameworks, that is why I calculated programming languages popularity instead.

# The author :

I am [Tariq BOUKOUYEN](https://www.linkedin.com/in/tariq-boukouyen/), a computer science engineering student.

# License :

[MIT](https://github.com/TariqBkn/gemography_backend_coding_challenge/blob/master/LICENSE.md)
