# Hornby

Are the trains running?

### Running

You need to download and install `sbt` for this application to run.

Also, in conf directory, add a file named `key.conf`. In there, add

```aidl
apiKey=YOUR_API_KEY
```

Once you have sbt installed, the following at the command prompt will start up Play in development mode:

```bash
sbt run
```

Play will start up on the HTTP port at <http://localhost:9000/>.   You don't need to deploy or reload anything -- changing any source code while the server is running will automatically recompile and hot-reload the application on the next HTTP request.
