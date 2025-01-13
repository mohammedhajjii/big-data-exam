package md.hajji.springbatchex.configurations;

import md.hajji.springbatchex.models.Sale;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class SaleMapper {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Bean
    public FieldSetMapper<Sale> saleFieldSetMapper(){
        return fieldSet -> Sale.builder()
                .transactionId(fieldSet.readLong(0))
                .productName(fieldSet.readString(1))
                .category(fieldSet.readString(2))
                .quantity(fieldSet.readInt(3))
                .unitPrice(fieldSet.readDouble(4))
                .purchaseDate(LocalDate.parse(fieldSet.readString(5), FORMATTER))
                .build();
    }


    @Bean
    public LineTokenizer lineTokenizer(){
        var tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"transactionId", "productName", "category", "quantity", "unitPrice", "purchaseDate"});
        return tokenizer;
    }


    @Bean
    public LineMapper<Sale> saleLineMapper(
            LineTokenizer tokenizer,
            FieldSetMapper<Sale> saleFieldSetMapper){

        var defaultLineMapper = new DefaultLineMapper<Sale>();
        defaultLineMapper.setLineTokenizer(tokenizer);
        defaultLineMapper.setFieldSetMapper(saleFieldSetMapper);
        return defaultLineMapper;
    }
}
