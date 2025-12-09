HTTP: HyperText Transfer Protocol

Used to facilitate communication to Server - anything
(application/process/program) that is waiting and listening 
for a request

Client -  whatever thing (person/application/process) that 
makes a request and starts the conversation

HTTP Requests - Header and Body

Header - Metadata (the data about your message)

Body - the actual message

HTTP Methods / HTTP Verbs:

GET (H) - place a request for a resource, just needs a header not body, causes the server to send the body

POST (H/B) - create a new resource from content of the body, needs header and body
"posting on a message board"

PUT - update (replace content) a resource from the content of the body, needs header and body

PATCH - partial update (amending content) a resource from the content of the body, needs header and body

DELETE - just needs header, not body. request a resource be destroyed

OPTIONS - just needs header, request a list of the types of 
requests that a server can handle/respond

HEAD (H) - similar to get, requesting only the metadata (header) of the response. checks to make sure resource is not huge

GET, OPTIONS and HEAD are safe.
Rest are unsafe.

Idempotent doing same action again and again 
will result in the same response.
All are idempotent except Patch.

Everything that is safe is idempotent but not
everything that is idempotent is safe. 

HTTP Response Codes:
1xx -  informational response

2xx - successful response/operation

3xx - Redirection (mail forwarding)

4xx - Client side errors (the system placing the request)

5xx - Server side errors (the application answering the request)
