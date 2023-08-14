package com.e.store.media.reposities;

import com.e.store.media.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogRepositories extends JpaRepository<Blog, String> {

}
