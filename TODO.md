Required
--------
* akka-http (DONE)
* Better database migrations (DONE)
* Testing:
  * Unit testing
  * Integration testing (routes)
    Note: Will this work? Will we not need a database set up? Could we mock the database instead?
  * Acceptance / end-to-end testing
  * Documentation generation
* Code coverage analysis

Nice to have
------------
* Metrics
* Auditing to logstash (need generic JSON support?)
* Support for form POSTs
  * This is already present but broken by the JsonSupport.
  * https://github.com/hseeberger/akka-http-json/issues/148
* Support for logback-access (open-source project?)
