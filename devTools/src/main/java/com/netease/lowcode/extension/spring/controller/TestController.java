package com.netease.lowcode.extension.spring.controller;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @GetMapping("/rest/test/vm")
    public Object list() throws IOException, AttachNotSupportedException {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        List<StringBuilder> vmList = new ArrayList<>();
        for (VirtualMachineDescriptor virtualMachineDescriptor : list) {
            StringBuilder sb = new StringBuilder();
            sb.append("id=").append(virtualMachineDescriptor.id()).append(";;;;");
            sb.append("displayName=").append(virtualMachineDescriptor.displayName()).append(";;;;");
            sb.append("string=").append(virtualMachineDescriptor.toString());
            vmList.add(sb);
        }
        return vmList;
    }

    @GetMapping("/rest/test/attach")
    public Object attach() throws IOException, AttachNotSupportedException {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vm : list) {
            if(StringUtils.isBlank(vm.displayName())){
                continue;
            }
            if(StringUtils.endsWith(vm.displayName(),".Application")){
                // jdk的版本 要对应
                VirtualMachine attach = VirtualMachine.attach(vm);
                return attach.getSystemProperties();
            }
        }
        return null;
    }
}
