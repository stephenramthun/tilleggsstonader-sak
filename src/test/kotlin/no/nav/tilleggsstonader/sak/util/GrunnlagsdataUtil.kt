package no.nav.tilleggsstonader.sak.util

import no.nav.tilleggsstonader.sak.opplysninger.grunnlag.Grunnlag
import no.nav.tilleggsstonader.sak.opplysninger.grunnlag.GrunnlagBarn
import no.nav.tilleggsstonader.sak.opplysninger.grunnlag.Grunnlagsdata
import no.nav.tilleggsstonader.sak.opplysninger.grunnlag.Navn
import java.time.LocalDate
import java.util.UUID

object GrunnlagsdataUtil {
    fun grunnlagsdataDomain(
        behandlingId: UUID = UUID.randomUUID(),
        grunnlag: Grunnlag = lagGrunnlagsdata(),
    ) = Grunnlagsdata(
        behandlingId = behandlingId,
        grunnlag = grunnlag,
    )

    fun lagGrunnlagsdata(
        navn: Navn = lagNavn(),
        barn: List<GrunnlagBarn> = listOf(lagGrunnlagsdataBarn()),
    ) = Grunnlag(
        navn = navn,
        barn = barn,
    )

    fun lagGrunnlagsdataBarn(
        ident: String = "1",
        navn: Navn = lagNavn(),
        fødselsdato: LocalDate = LocalDate.now(),
        dødsdato: LocalDate? = null,
    ) = GrunnlagBarn(
        ident = ident,
        navn = navn,
        alder = antallÅrSiden(fødselsdato),
        fødselsdato = fødselsdato,
        dødsdato = dødsdato,
    )

    fun lagNavn(
        fornavn: String = "Fornavn",
        mellomnavn: String? = "mellomnavn",
        etternavn: String = "Etternavn",
    ): Navn {
        return Navn(
            fornavn = fornavn,
            mellomnavn = mellomnavn,
            etternavn = etternavn,
        )
    }
}
