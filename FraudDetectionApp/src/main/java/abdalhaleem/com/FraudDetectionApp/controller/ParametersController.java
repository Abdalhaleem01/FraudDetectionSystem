package abdalhaleem.com.FraudDetectionApp.controller;

import abdalhaleem.com.FraudDetectionApp.model.ParametersModel;
import abdalhaleem.com.FraudDetectionApp.service.ParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ParametersController {


    private final ParametersService parametersService;

    public ParametersController (ParametersService parametersService){
        this.parametersService = parametersService;
    }

    @GetMapping("parameters")
    public ParametersModel getAllParameters() {

        return parametersService.getParameters();
    }

    @PostMapping("parameters")
    public ParametersModel updateParameters(@RequestBody ParametersModel parameters) {
        parametersService.updateParameters(parameters);

        return parameters;
    }

    @DeleteMapping("parameters")
    public String deleteParameters() {

        parametersService.deleteParameters();
        return "Parameters have been deleted successfully";
    }

}
