package service;

import dao.ProductCategoryDao;
import domain.ProductCategory;

import java.util.List;

public class ProductCategoryService {

    private static final ProductCategoryDao productCategoryDao = new ProductCategoryDao();

    public List<ProductCategory> findMainCategories() {
        return productCategoryDao.findByParentIdIsNull();
    }

    public List<ProductCategory> findSubCategories(Integer parentId) {
        return productCategoryDao.findByParentId(parentId);
    }
}
