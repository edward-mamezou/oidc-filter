package envoy.authz

import input.attributes.request.http as http_request

default allow = false

role = role {
    auth_header := http_request.headers["authorization"]
    value := split(auth_header, " ")
    jwt := io.jwt.decode(value[1])
    role := jwt[1]["name"]
}

allow = response {
    http_request.method == "GET"
    http_request.path == "/hello"
    response := {
        "allowed": true,
        "headers": {
            "x-custom-role": role
        } 
    } 
}