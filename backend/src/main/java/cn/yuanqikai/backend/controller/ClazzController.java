package cn.yuanqikai.backend.controller;

import cn.yuanqikai.backend.dto.ClassListDTO;
import cn.yuanqikai.backend.entity.Clazz;
import cn.yuanqikai.backend.response.DataResponse;
import cn.yuanqikai.backend.service.ClazzService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author yuanqikai
 * @date 2021-04-23 13:19
 */
@RestController
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @PostMapping("/api/class/add")
    public DataResponse addClass(@RequestBody Clazz clazz) {
        int count = clazzService.findRepeat(clazz);
        if (count != 0) {
            return DataResponse.fail().msg("班级已存在");
        }
        int res = clazzService.addClazz(clazz);
        if (res != 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/class/all")
    public DataResponse searchAll() {
        List<Clazz> clazzList = clazzService.selectAll();
        Set<String> grades = new HashSet<>();
        Set<String> major = new HashSet<>();
        Set<String> className = new HashSet<>();

        if(clazzList.size() > 0) {
            for(Clazz clazz : clazzList) {
                grades.add(clazz.getGrade());
                major.add(clazz.getMajor());
                className.add(clazz.getClassName());
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("grade",grades);
            map.put("major",major);
            map.put("className",className);
            return DataResponse.success().data(map);
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/class/list")
    public DataResponse searchClass(@RequestBody Clazz clazz) {
        Page<Clazz> clazzList = clazzService.selectMultiple(clazz);
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageData", clazzList);
        map.put("pageTotal", clazzList.getTotal());
        return DataResponse.success().data(map);
    }

    @GetMapping("/api/class/findById")
    public DataResponse findById(@RequestParam Integer classId) {
        Clazz clazz = clazzService.selectById(classId);
        return DataResponse.success().data(clazz);
    }

    @GetMapping("/api/class/delete")
    public DataResponse deleteById(@RequestParam Integer classId) {
        int res = clazzService.deleteById(classId);
        if(res != 0) {
            return DataResponse.success();
        }
        return DataResponse.fail();
    }

    @PostMapping("/api/class/update")
    public DataResponse updateClass(@RequestBody Clazz clazz) {
        int count = clazzService.findRepeat(clazz);
        if (count != 0) {
            return DataResponse.fail().msg("班级已存在");
        }
        int res = clazzService.updateClazz(clazz);
        if (res != 0) {
            return DataResponse.success();
        }

        return DataResponse.fail();
    }

    @PostMapping("/api/class/getAll")
    public DataResponse getAllClass() {
        List<Clazz> clazzList = clazzService.selectAll();
        ArrayList<ClassListDTO> classListDTOS = new ArrayList<>();
        for(Clazz clazz:clazzList) {
            ClassListDTO classListDTO = new ClassListDTO();
            String ClassTitle = clazz.getMajor() + clazz.getClassName() + "(" + clazz.getGrade() +")";
            classListDTO.setClassId(clazz.getClassId());
            classListDTO.setClassTitle(ClassTitle);
            classListDTOS.add(classListDTO);
        }
        return DataResponse.success().data(classListDTOS);
    }
}
