package no.nav.tilleggsstonader.sak.vilkår.regler

import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype
import no.nav.tilleggsstonader.sak.vilkår.domain.VilkårType
import no.nav.tilleggsstonader.sak.vilkår.regler.vilkår.AktivitetRegel
import no.nav.tilleggsstonader.sak.vilkår.regler.vilkår.AktivitetTiltakRegel
import no.nav.tilleggsstonader.sak.vilkår.regler.vilkår.AktivitetUtdanningRegel
import no.nav.tilleggsstonader.sak.vilkår.regler.vilkår.MålgruppeAAPFerdigAvklartRegel
import no.nav.tilleggsstonader.sak.vilkår.regler.vilkår.MålgruppeAAPRegel
import no.nav.tilleggsstonader.sak.vilkår.regler.vilkår.MålgruppeRegel
import no.nav.tilleggsstonader.sak.vilkår.regler.vilkår.PassBarnRegel

/**
 * Singleton for å holde på alle regler
 */
class Vilkårsregler private constructor(val vilkårsregler: Map<VilkårType, Vilkårsregel>) {

    companion object {

        val ALLE_VILKÅRSREGLER = Vilkårsregler(alleVilkårsregler.associateBy { it.vilkårType })
    }
}

private val alleVilkårsregler = Stønadstype.entries.map { vilkårsreglerForStønad(it) }.flatten()

fun vilkårsreglerForStønad(stønadstype: Stønadstype): List<Vilkårsregel> =
    when (stønadstype) {
        Stønadstype.BARNETILSYN -> listOf(
            MålgruppeAAPRegel(),
            MålgruppeAAPFerdigAvklartRegel(),
            MålgruppeRegel(),
            AktivitetRegel(),
            AktivitetTiltakRegel(),
            AktivitetUtdanningRegel(),
            PassBarnRegel(),
        )
    }

fun hentVilkårsregel(vilkårType: VilkårType): Vilkårsregel {
    return Vilkårsregler.ALLE_VILKÅRSREGLER.vilkårsregler[vilkårType]
        ?: error("Finner ikke vilkårsregler for vilkårType=$vilkårType")
}
