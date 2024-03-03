package ericrlessa.rinhabackend;


import ericrlessa.rinhabackend.domain.Cliente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractTest {

    @BeforeEach
    @Transactional
    public void beforeEach(){

        Cliente.deleteAll();
        Cliente.of(1l, 100000l).persist();
        Cliente.of(2l, 80000l).persist();
        Cliente.of(3l, 1000000l).persist();
        Cliente.of(4l, 10000000l).persist();
        Cliente.of(5l, 500000l).persist();

    }
}
