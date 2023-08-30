package org.example.Server.parsingPort;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class ReqArguments {
    @Parameter(
            names = {"--port"},
            description = "Port number",
            required = true
    )
    private String name;
        public String getName() {
            return name;
        }

}
