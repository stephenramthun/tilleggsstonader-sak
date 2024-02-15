package no.nav.tilleggsstonader.sak.utbetaling.tilkjentytelse

import no.nav.tilleggsstonader.sak.utbetaling.tilkjentytelse.domain.AndelTilkjentYtelse
import no.nav.tilleggsstonader.sak.utbetaling.tilkjentytelse.domain.Satstype
import no.nav.tilleggsstonader.sak.utbetaling.tilkjentytelse.domain.TilkjentYtelse
import no.nav.tilleggsstonader.sak.utbetaling.tilkjentytelse.domain.TypeAndel
import no.nav.tilleggsstonader.sak.util.min
import java.time.LocalDate
import java.util.UUID

object TilkjentYtelseUtil {

    fun tilkjentYtelse(
        behandlingId: UUID,
        startdato: LocalDate? = null,
        vararg andeler: AndelTilkjentYtelse = arrayOf(andelTilkjentYtelse(kildeBehandlingId = behandlingId)),
    ): TilkjentYtelse {
        return TilkjentYtelse(
            behandlingId = behandlingId,
            startdato = min(startdato, andeler.minOfOrNull { it.fom }) ?: error("Må sette startdato"),
            andelerTilkjentYtelse = andeler.toSet(),
        )
    }

    fun andelTilkjentYtelse(
        kildeBehandlingId: UUID,
        beløp: Int = 11554,
        fom: LocalDate = LocalDate.of(2021, 1, 1),
        tom: LocalDate = LocalDate.of(2021, 1, 31),
        satstype: Satstype = Satstype.DAG,
        type: TypeAndel = TypeAndel.TILSYN_BARN_AAP,
    ) = AndelTilkjentYtelse(
        beløp = beløp,
        fom = fom,
        tom = tom,
        satstype = satstype,
        type = type,
        kildeBehandlingId = kildeBehandlingId,
    )
}
