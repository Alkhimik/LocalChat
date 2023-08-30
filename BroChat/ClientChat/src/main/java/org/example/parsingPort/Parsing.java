package org.example.parsingPort;
import com.beust.jcommander.JCommander;
public class Parsing{
        private String[] args;
        public Parsing(String[] args){
                this.args = args;
        }
        public String getPort(){
                ReqArguments req = new ReqArguments();
                JCommander com = JCommander.newBuilder().addObject(req).build();
                com.parse(args);
                return req.getName();
        }
}
