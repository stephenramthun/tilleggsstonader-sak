package no.nav.tilleggsstonader.sak.util

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition

fun hasCauseMessageContaining(msg: String) =
    Condition<Throwable>({
        val message = it.cause?.message ?: error("Mangler cause/message")
        assertThat(message).contains(msg)
        true
    }, "")
