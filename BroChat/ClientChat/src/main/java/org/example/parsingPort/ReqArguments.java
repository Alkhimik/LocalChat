package org.example.parsingPort;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class ReqArguments {
    @Parameter(
            names = {"--server-port"},
            description = "ServerPort number",
            required = true
    )
    private String name;
        public String getName() {
            return name;
        }

}
