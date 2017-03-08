h2. What is this project ?

This project *cas-oauth-demo-3.5.x* has been created to test the OAuth support in *CAS server version 3.5.x with x >= 1*. It's composed of two modules :
- the *cas-oauth-client-demo-3.5.x* module is a CAS server which uses the OAuth client mode : it acts as a client to delegate authentication to Facebook, Twitter... : "https://wiki.jasig.org/display/CASUM/OAuth+client+support+for+CAS+server+version+%3E%3D+3.5.1":https://wiki.jasig.org/display/CASUM/OAuth+client+support+for+CAS+server+version+%3E%3D+3.5.1
- the *cas-oauth-server-demo-3.5.x* module is a CAS server which uses the OAuth server mode : it plays the role of an OAuth server : "https://wiki.jasig.org/display/CASUM/OAuth+server+support":https://wiki.jasig.org/display/CASUM/OAuth+server+support.

h2. Quick start & test

To start quickly, build the project :<pre><code>cd cas-oauth-demo-3.5.x
mvn clean install</code></pre>and start the two web applications with jetty :<pre><code>cd cas-oauth-client-demo-3.5.x
mvn jetty:run</code></pre>

To test,
- call the "http://localhost:8080/cas":http://localhost:8080/cas url and click on "Authenticate with ..." (at CAS server in OAuth client mode)
- authenticate at your favorite OAuth provider (Facebook, Twitter...) or at the OAuth wrapped CAS server (same password as login, url : _http://localhost:8080/cas2_)
- be redirected to the first CAS server, being successfully authenticated.

h2. Manual deployment

You can also deploy manually these two web applications in your favorite web applications server :
- cas-oauth-client-demo-3.5.x on http://localhost:8080/cas
- cas-oauth-server-demo-3.5.x on http://localhost:8080/cas2
