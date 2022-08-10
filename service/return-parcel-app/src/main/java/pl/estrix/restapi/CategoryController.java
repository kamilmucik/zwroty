package pl.estrix.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.estrix.backend.category.dto.CategoryGroupDto;
import pl.estrix.backend.category.service.CategoryGroupService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryGroupService categoryGroupService;

    @ResponseBody
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public List<CategoryGroupDto> getCategoryGroup() {
        return categoryGroupService.getCategoryGroup();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<CategoryGroupDto> get(@PathVariable("id") Long id) {
        return new ResponseEntity<>(categoryGroupService.get(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.PUT)
    public ResponseEntity<CategoryGroupDto> create(@RequestBody CategoryGroupDto categoryGroupDto) {
//        System.out.println("create.categoryGroupDto: " + categoryGroupDto);
        categoryGroupDto.setId(123L);
//        System.out.println("create.categoryGroupDto: " + categoryGroupDto);
        return new ResponseEntity<>(categoryGroupService.save(categoryGroupDto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
//        System.out.println("delete.categoryGroupDto: " + id);
//        categoryGroupService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CategoryGroupDto> update(@RequestBody CategoryGroupDto categoryGroupDto) {
//        System.out.println("update.categoryGroupDto: " + categoryGroupDto);
        return new ResponseEntity<>(categoryGroupService.save(categoryGroupDto), HttpStatus.OK);
    }

}
