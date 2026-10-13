package com.example.jpa.entities;

// Cycle de vie d'une reservation : une demande part a EN_ATTENTE, est validee en CONFIRMEE,
// ou peut etre ANNULEE a tout moment par l'utilisateur ou le service.
public enum StatutReservation {
    EN_ATTENTE,
    CONFIRMEE,
    ANNULEE
}
