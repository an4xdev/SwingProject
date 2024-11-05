package edu.uws.ii.project.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AddFormDTO {
    private Long id;
    private String name;
    private List<IngredientDTO> ingredients;
    private MultipartFile[] images;


    private List<IngredientDTO> ingredientsAdded;

    public AddFormDTO() {
    }

    public AddFormDTO(Long id, String name, List<IngredientDTO> ingredients, List<IngredientDTO> ingredientsAdded) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.ingredientsAdded = ingredientsAdded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
    public List<IngredientDTO> getIngredientsAdded() {
        return ingredientsAdded;
    }

    public void setIngredientsAdded(List<IngredientDTO> ingredientsAdded) {
        this.ingredientsAdded = ingredientsAdded;
    }

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] image) {
        this.images = image;
    }
}
