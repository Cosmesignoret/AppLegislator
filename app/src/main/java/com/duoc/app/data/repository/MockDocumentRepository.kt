package com.duoc.app.data.repository

import com.duoc.app.data.model.LegalDocument
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockDocumentRepository : DocumentRepository {

    private val documents = listOf(
        LegalDocument(
            id = "1",
            title = "Constitution - Article 19",
            category = "Constitutional Law",
            content = "The Constitution guarantees to all persons:\n\n" +
                "1. The right to life and to physical and mental integrity.\n\n" +
                "2. Equality before the law. In Chile there are no privileged persons or groups.\n\n" +
                "3. Equal protection of the law in the exercise of their rights.\n\n" +
                "4. Respect and protection of private life and honor of the person and their family."
        ),
        LegalDocument(
            id = "2",
            title = "Civil Code - Article 1,545",
            category = "Civil Law",
            content = "A sale is a contract in which one party agrees to give a thing and the other " +
                "to pay for it in money. The former is said to sell and the latter to buy. " +
                "The money given by the buyer for the thing sold is called the price."
        ),
        LegalDocument(
            id = "3",
            title = "Criminal Code - Article 1",
            category = "Criminal Law",
            content = "A crime is any voluntary action or omission punished by law.\n\n" +
                "Actions or omissions punished by law are always presumed voluntary, " +
                "unless the contrary is proven. Whoever commits a crime shall be responsible for it " +
                "and shall incur the penalty established by law, even if the harm falls on a person " +
                "other than the one intended."
        ),
        LegalDocument(
            id = "4",
            title = "Labor Code - Article 1",
            category = "Labor Law",
            content = "Labor relations between employers and workers shall be governed " +
                "by this Code and its complementary laws.\n\n" +
                "These rules shall not apply, however, to officials of the State Administration, " +
                "centralized or decentralized, to whom the Administrative Statute shall apply."
        ),
        LegalDocument(
            id = "5",
            title = "Law 19,496 - Consumer Rights",
            category = "Commercial Law",
            content = "Consumer rights:\n\n" +
                "1) Free choice of goods or services.\n" +
                "2) The right to truthful and timely information.\n" +
                "3) The right not to be arbitrarily discriminated against.\n" +
                "4) Safety in the consumption of goods or services."
        )
    )

    override fun getDocuments(): Flow<List<LegalDocument>> = flowOf(documents)

    override fun getDocument(id: String): Flow<LegalDocument?> =
        flowOf(documents.firstOrNull { it.id == id })
}
