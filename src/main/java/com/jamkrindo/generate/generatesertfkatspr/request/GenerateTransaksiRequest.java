package com.jamkrindo.generate.generatesertfkatspr.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenerateTransaksiRequest implements Serializable {

    @NotNull(message = "id dd bank harus diisi")
    private int id_dd_bank;

    @JsonProperty("id_dd_bank")
    public int getDdBank(){return id_dd_bank;}

}
