package ec.com.zurich.suscription.resources.entity;

public enum EndosoTipoCancelacion {
    CalculoContrarioEnsurancePorFaltaDePago("D"),
    CalculoContrarioEnsuranceAutomaticoPorFaltaDePago("ED");

    private final String value;

    EndosoTipoCancelacion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
