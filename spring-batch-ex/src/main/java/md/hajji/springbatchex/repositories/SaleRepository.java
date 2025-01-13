package md.hajji.springbatchex.repositories;


import md.hajji.springbatchex.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository  extends JpaRepository<Sale, Long> {
}
