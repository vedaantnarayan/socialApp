# Social App

Social App is a spring boot project for dealing with capturing feed of different social network and persist it to Mongo DB.

## Dependencies

We have used following dependencies in project :-

```
* Spring Boot
* Spring Social
* Guava
* Gson
```

## Execution Command
     mvn spring-boot:run

## Usage


It is meant for collecting data from different-different 
social sites and store it to mongo database for further
analysis and uses.(Spring Scheduler has been used to 
invoke service for collecting data)



## API
Followings are the list of api and its inputs :

To Create User in mongo DB :

=> URL: http://localhost:8102/user/create (Post)

Input :

{
  "isActive": "1",
  "email": "social.app09@gmail.com",
  "phone": "+91 9755742695",
  "firstName": "Vedant",
  "lastName": "Narayan",
  "details": "Some more details,if needed",
  "userTokenDetails": [
    {
      "source": "Twitter",
      "accessToken": "1076049207616724992-mrWvkmlB",
      "accessTokenSecret": "VQNXnZ8vEGAAqYp0sPe6UEw",
      "userName": "@SocialApp10",
      "details": "Some more details,if needed"
    },
    {
      "source": "Facebook",
      "accessToken": "EAAgNSJHmBpMBALOWQe0ZBgv43nvcZBrzM8i65PVB5fOks8DagZCfZBZC4pY4Mi2sos55fH74FN1QDIaLzliA5OAZCrIZCFOjqMo7B8iQJDhTmLFTBhDHeOi9qdmaILp",
      "accessTokenSecret": "",
      "userName": "veda.sapp.9",
      "details": "Some more details,if needed"
    }
  ]
}

To Get All users :

=> URL: http://localhost:8102/user/getAllUsers  (Get)

To Tweet Something In User Account :

=> URL : http://localhost:8102/user/tweet (Post)

Input : 

{
"text": "This is my Fourth tweet. #my4thTweet"
}

To Get All Twitter feeds :

=> URL : http://localhost:8102/user/twitter/feed (Get)

To Post Something In User Account :

=> URL : http://localhost:8102/user/fb/post (Post)

Input : 

{
"text": "This is my Second Post on FB. #my2ndPost"
}



To Get All Facebook Posts : 

=> URL : http://localhost:8102/user/fb/feed (Get)

##

## Note 
On Create User API's input accessToken and accessTokenSecret has been changed for security purpose.

##

