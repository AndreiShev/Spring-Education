package com.example.rest.web.controller;


import com.example.rest.mapper.NewsCategoryMapper;
import com.example.rest.model.NewsCategory;
import com.example.rest.services.NewsCategoryService;
import com.example.rest.web.model.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/newsCategories")
public class NewsCategoryController {
    private final NewsCategoryMapper newsCategoryMapper;
    private final NewsCategoryService newsCategoryServiceImpl;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<NewsCategoryListResponse> findAll(@RequestBody @Valid RequestGetAll request) {
        return ResponseEntity.ok(
                newsCategoryMapper.newsCategoryListToNewsCategoryResponseList(
                        newsCategoryServiceImpl.findAll(request.getPageNumber(), request.getLimit())
                )
        );
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<NewsCategoryResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(
                newsCategoryMapper.newsCategoryToResponse(
                        newsCategoryServiceImpl.findById(id)
                )
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<NewsCategoryResponse> create(@RequestBody @Valid UpsertNewsCategoryRequest request) {
        NewsCategory newsCategory = newsCategoryServiceImpl.save(newsCategoryMapper.requestToNewsCategory(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(newsCategoryMapper.newsCategoryToResponse(newsCategory));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<NewsCategoryResponse> update(@PathVariable("id") Integer userId,
                                               @RequestBody @Valid UpsertNewsCategoryRequest request) {
        NewsCategory updatedCategory = newsCategoryServiceImpl.update(newsCategoryMapper.requestToNewsCategory(userId, request));

        return ResponseEntity.ok(newsCategoryMapper.newsCategoryToResponse(updatedCategory));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        newsCategoryServiceImpl.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
