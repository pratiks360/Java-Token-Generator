package io.generic.tokenservice.repository;

import io.generic.tokenservice.model.TokenDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TokenServieRepo extends CrudRepository<TokenDTO, Long> {

    @Query("FROM TOKEN_MASTER t where t.customerId = :customerId and t.channel =:channel order by time desc ")
    List<TokenDTO> findCustomerToken(@Param("customerId") String customerId, @Param("channel") String channel);


}
