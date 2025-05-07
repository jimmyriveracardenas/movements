package com.tcs.technicaltest.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientTest {

    @Test
    void builder_createsClientWithAllFields() {
        // Preparo una Person simulada
        Person person = Person.builder()
                .personId(42L)
                .name("Alice")
                .gender("F")
                .age(30)
                .identification("ABC123")
                .address("123 Main St")
                .phone("0999000111")
                .build();

        // Construyo un Client usando el builder de Lombok
        Client client = Client.builder()
                .clientId(100L)
                .person(person)
                .password("secretPwd")
                .status(true)
                .build();

        // Verifico cada campo
        assertThat(client.getClientId()).isEqualTo(100L);
        assertThat(client.getPerson()).isSameAs(person);
        assertThat(client.getPassword()).isEqualTo("secretPwd");
        assertThat(client.getStatus()).isTrue();

        // Además, al obtener la person desde Client, debe coincidir
        assertThat(client.getPerson().getName()).isEqualTo("Alice");
    }
}
