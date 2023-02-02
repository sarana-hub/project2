package hello.springmvcjpa.web.item;

import hello.springmvcjpa.domain.item.UploadFile;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemForm {

    private Long id;

    private String itemName;

    @NumberFormat(pattern = "###,###")
    private Integer price;

    @NumberFormat(pattern = "###,###")
    private Integer stockQuantity;

    private String shop; //매장명

    @OneToMany
    private List<UploadFile> imageFiles=new ArrayList<>();
    //private List<MultipartFile> imageFiles;


}
