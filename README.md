## BE-MilliporeSigma (Boston Engineering)
Boston Engineering Login Module



####  JSON Web Token(JWT)
JSON Web Token (JWT) is an open standard (RFC 7519) that defines a compact and self-contained way for securely transmitting information between parties as a JSON object.a stateless authentication mechanism as the user state is never saved in server memory.A JWT token consists of 3 parts seperated with a dot(.) i.e. Header.payload.signature

**Header** has 2 parts type of token and hashing algorithm used.The JSON structure comprising these two keys are Base64Encoded.
```java
{
  "alg": "HS256",
  "typ": "JWT"
}
```
**Payload** contains the claims.Primarily, there are three types of claims: reserved, public, and private claims. Reserved claims are predefined claims such as iss (issuer), exp (expiration time), sub (subject), aud (audience).In private claims, we can create some custom claims such as subject, role, and others.
```java
{
  "sub": "Alex123",
  "scopes": [
    {
      "authority": "ROLE_ADMIN"
    }
  ],
  "iss": "http://devglan.com",
  "iat": 1508607322,
  "exp": 1508625322
}


```
#### REST APIs Screenshots

![Screenshots](https://dl.dropbox.com/s/2tqmrytdkzv2dk3/1.png?raw=1)

![Screenshots](https://dl.dropbox.com/s/r2timkoyop0i413/2.png?raw=1)


![Screenshots](https://dl.dropbox.com/s/9bavyj85x6b0zq6/3.png?raw=1)
