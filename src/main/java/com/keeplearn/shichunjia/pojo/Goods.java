package com.keeplearn.shichunjia.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "jd_goods",type = "goods")
public class Goods implements Serializable{

    @Id   //映射id
    private String id;
    @Field(type = FieldType.Keyword)
    private String img;
    @Field(type = FieldType.Keyword)
    private String title;
    @Field(type = FieldType.Keyword)
    private String store;
    @Field(type = FieldType.Keyword)
    private String price;
    @Field(type = FieldType.Date)
    private Date createDate;


    @Override
    public String toString() {
        return "Goods{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", store='" + store + '\'' +
                ", price='" + price + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
