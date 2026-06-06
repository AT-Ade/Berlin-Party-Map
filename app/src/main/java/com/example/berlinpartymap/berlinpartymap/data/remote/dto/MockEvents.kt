package com.example.berlinpartymap.berlinpartymap.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val name: String
)


@Serializable
data class Event(
    val name: String,
    val venueName: String,
    val venueAddress: String,
    val latitude: Double,
    val longitude: Double,
    val startTime: String, // ISO 8601 Format
    val endTime: String,
    val lineup: List<Artist>,
    val description: String,
    val url: String,
    val flyerURL: String,
    val price: Double
)

val berlinEvents = listOf(

    Event(
        name = "Golden Groove x Kollektiv Nachtkerze",
        venueName = "Jonny Knüppel",
        venueAddress = """Lilli-Henoch-Straße 10
10405 Berlin""",
        latitude = 52.5306,
        longitude = 13.4247,
        startTime = "2026-05-30T15:00:00+02:00",
        endTime = "2026-05-31T10:00:00+02:00",
        lineup = listOf(
            Artist("Andy's Echo"), Artist("ATTA (GER)"), Artist("Bretter Max"),
            Artist("David Silver"), Artist("diladï"), Artist("FRÆDO"),
            Artist("Josefina Tapia"), Artist("Joshua Liebe"), Artist("LANA:YEN"),
            Artist("Lasse Tanzn"), Artist("Leon Licht"), Artist("madmoiselle"),
            Artist("Maltesar"), Artist("Pabel"), Artist("Pippi KK"),
            Artist("Rosa Kante"), Artist("Sven Weisemann"), Artist("Tekla"),
            Artist("Wolperdinger")
        ),
        description = """Es ist endlich wieder soweit. Unser geländeeigenes Kollektiv Nachtkerze vereint sich mit Golden Groove für eine weitere, unvergessliche Zeit der Extase im JK. Mit exquisiten künstlerischen Darbietungen und gewohnt liebenswürdiger Nonchalance übernehmen die beiden Truppen für einen Tag und eine Nacht alle zur Verfügung stehenden Tanzflächen!
 
Was im letzten Jahr mehrfach für durchweg grinsende Gesichter und durchgeschwitzte Leiber gesorgt hat, hat auch dieses Jahr keine Mühen gescheut, um für alles zu sorgen, was das Tänzer*innenherz begehrt!
 
NK & GG freuen sich auf einen weiteren wilden Ritt und empfangen euch mit einem sorgsam kuratierten Line Up.""",
        url = "https://de.ra.co/events/2418248",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vOWZlOTk4ZDM4ZTFlNDIwMjhiNjUzNzliOWJhYzYwYjk0MmRlY2FmYS5qcGc=",
        price = 25.0 // kein Preis auf RA angegeben
    ),

    Event(
        name = "Silk",
        venueName = "Fitzroy",
        venueAddress = """Holzmarktstraße 15
10179 Berlin""",
        latitude = 52.5132,
        longitude = 13.4163,
        startTime = "2026-05-30T23:00:00+02:00",
        endTime = "2026-05-31T06:00:00+02:00",
        lineup = listOf(
            Artist("ALKARLINE"), Artist("BUTZ"),
            Artist("multibliss"), Artist("Dynamic Experience")
        ),
        description = "Between body and sound. Forms blur, reappear, and never quite settle. Caught somewhere in the rhythm. Techno & House. 18+",
        url = "https://de.ra.co/events/2425571",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vYmM2M2ViMzA4YTY5MmUwMTIyYWE2MTViZWZmZTQ0NjY1YTE2OWM3YS5wbmc=",
        price = 25.0 // kein Preis auf RA angegeben
    ),

    // ── Mittwoch, 27. Mai 2026 ───────────────────────────────────────────────

    Event(
        name = "POPPERS ROBBED",
        venueName = "Monster Ronson's Ichiban Karaoke",
        venueAddress = """Warschauer Str. 34
10243 Berlin""",
        latitude = 52.5013,
        longitude = 13.4536,
        startTime = "2026-05-27T20:00:00+02:00",
        endTime = "2026-05-27T23:59:00+02:00",
        lineup = listOf(
            Artist("BLEACH.LIVE"), Artist("Beyefendi"), Artist("Fagatha Crispy"),
            Artist("Juicyca"), Artist("Oriana Nye"), Artist("Señorita Toyota Corolla"),
            Artist("POPPERS PAGEANT"), Artist("Laye Lowe"), Artist("Lili Alexander"),
            Artist("Vivide Romance"), Artist("KARAOKE - Oozing Gloop")
        ),
        description = """⭑DANCE MUSIC DRAG SHOW⭑

POPPERs ROBBED with all those incredible performers that didn't win the first trophy return to the POPPERs stage! They have all been performing the city over and now return to the Dance Music Drag Show celebrating homoeroticism, club culture and sexual liberation.

The show starts with the POPPERs PAGEANT where Berlin's hottest performers compete for a POPPERs bottle. With one winner going on to the grand POPPERs Pageant final in September!

Doors ⭑ 20:00
Shows ⭑ 21:00
Karaoke ⭑ 23:00""",
        url = "https://de.ra.co/events/2330753",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vODY4NGUzOWQ0NmVlYWUyMmJhMjYyMThiMTk0NGI4ZTc2NDk2NDU1Zi5wbmc=",
        price = 15.0
    ),

    Event(
        name = "SIGNALS - with DJ WIFI, Krash Cora & VLUNA",
        venueName = "Lokschuppen Berlin",
        venueAddress = """Revaler Straße 99 (Zugang über Warschauer Brücke)
10245 Berlin""",
        latitude = 52.5066,
        longitude = 13.4554,
        startTime = "2026-05-27T23:00:00+02:00",
        endTime = "2026-05-28T05:00:00+02:00",
        lineup = listOf(
            Artist("DJ WIFI"), Artist("Krash Cora"), Artist("VLUNA")
        ),
        description = """Be ready for another midweek madness. SIGNALS is a community event. Every Wednesday, with presale tickets for just 5€. See you in the first row - the community is everything for us!""",
        url = "https://de.ra.co/events/2351942",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vN2U0OGJhMzU5NTExOGMyODk5N2YxYzZhMTE4MDVhNWU2Zjc0NDM4My5qcGc=",
        price = 5.0
    ),

    Event(
        name = "TECHNO MITTWOCH",
        venueName = "Sensorium",
        venueAddress = """Warschauer Platz 18
10245 Berlin""",
        latitude = 52.5061,
        longitude = 13.4531,
        startTime = "2026-05-27T23:00:00+02:00",
        endTime = "2026-05-28T08:00:00+02:00",
        lineup = listOf(
            Artist("Zona Interdunar"), Artist("Grippe"), Artist("RON B"),
            Artist("Ssamel yam"), Artist("SENSORIUM RESIDENTS")
        ),
        description = """"Techno Mittwoch ist unser wöchentlicher Reset-Button: ein safer, offener Raum mitten in der Woche, wo der Alltag draußen bleibt. Keine starren Regeln, keine Blicke, keine Urteile – nur du, die Beats und Menschen, die genau wie du einfach tanzen wollen. Wir stehen für Consent, Mutual Respect und Zero Tolerance gegenüber jeglicher Form von Diskriminierung."

Awareness-Team vor Ort. 18+""",
        url = "https://de.ra.co/events/2398933",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZTY5MmFmMWQ4NGY0MzA5NDE5MDhmNmFhZDc2MWJjNTZiNTQxMzczZC5qcGc=",
        price = 10.0
    ),

    Event(
        name = "SYMBIOTIKKA at KitKat Club",
        venueName = "KitKatClub",
        venueAddress = """Köpenicker Straße 76
10179 Berlin""",
        latitude = 52.5038,
        longitude = 13.4241,
        startTime = "2026-05-27T22:00:00+02:00",
        endTime = "2026-05-28T06:00:00+02:00",
        lineup = listOf(
            Artist("MARCEL db"), Artist("DJ Jordan"), Artist("LeoSkiDj"), Artist("Dennis Rema")
        ),
        description = """★★★ SYMBIOTIKKA ★★★ The Wednesday at KitKat Club!

Celebrate with us Respect, Freedom, Love and Music! Electronic Music Entertainment.

Awareness Team - Bondage Show live - Playspace - Bodypainting - Chill Area - Kinky Shows live - Pool

Creative Dresscode: Be particular, creative, sexy, wicked, kinky, especially, or crazy. But never boring!

Afterhour Party with DJs from 6am to 13pm at PRISMA Bar, same building (Brückenstraße 1).

Sex-positive and LGBTQIA+ friendly. No GHB. No photos.""",
        url = "https://de.ra.co/events/2427273",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMTI5MTMwMzgyMmE1ZDBjYmY3YjJmMzcwMTY0ODRkNTBjY2E4ZDU5Yi5wbmc=",
        price = 25.0
    ),

    Event(
        name = "Longevity Rave x RESONANCE x NeuroCreate",
        venueName = "Georgia Bar",
        venueAddress = """Georgenstraße 194
10117 Berlin""",
        latitude = 52.5231,
        longitude = 13.3878,
        startTime = "2026-05-27T19:00:00+02:00",
        endTime = "2026-05-28T02:00:00+02:00",
        lineup = listOf(
            Artist("Tina Technotic"), Artist("Yukari"), Artist("Kazuki Takahashi"),
            Artist("Emilion Dollar Baby")
        ),
        description = """Some nights, the dancefloor revives you.

Longevity Rave is built around that feeling: a proper night out powered by good music, collective energy and the science behind why raving feels so good.

For one night at Georgia Bar, Longevity Rave joins forces with Berlin collective RESONANCE for a night of house and techno — dancing for health, connection and collective joy.

NeuroCreate will create live brainwave visualisations throughout the night, making Flow states visible and deepening immersion on the dancefloor.

Doors 19:00. From 17:00: optional pre-rave panel with scientists, DJs and cultural innovators.""",
        url = "https://de.ra.co/events/2432449",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vYjEwMDVmMzJkMzEyODhjZmViNmJlMjRjZjllOWQ2MTNmZmE3YjdmNi5wbmc=",
        price = 15.0 // kein Preis auf RA angegeben
    ),

    // ── Donnerstag, 28. Mai 2026 ─────────────────────────────────────────────

    Event(
        name = "House of Lunacy Chapter II. BLOOM",
        venueName = "Renate",
        venueAddress = """Alt Stralau 70
10245 Berlin""",
        latitude = 52.4990,
        longitude = 13.4670,
        startTime = "2026-05-28T21:00:00+02:00",
        endTime = "2026-05-29T12:00:00+02:00",
        lineup = listOf(
            Artist("House of Lunacy Residents & Performers")
        ),
        description = """Chapter II. BLOOM

House of Lunacy is an Adult playground for the senses, a sex positive space with consent and comfort at its principle core. A lovers Labyrinth of Art, Theatre and Performances from Berlins most creative souls.

DRESS CODE: BEASTS / BLOOMS / BIOMES / CREATURES / FLORA / FAUNA / FUNGHI / ORGANIC DEITIES / NYMPHS / FAWNS

No Effort, No Entry.

Consent is not negotiable. We DO NOT tolerate any form of discrimination, racism, homophobia, anti-trans or hate speech. Care and Awareness team on site at all times.

RA ticket does not guarantee entry. NO REFUNDS FOR NO COSTUME.""",
        url = "https://de.ra.co/events/2407132",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZGU3OTkyMzM1YjVlYmZiYzA0YTZlYWVmNTlmNzA1NzIzMzUxMjBjZi5wbmc=",
        price = 35.0
    ),

    Event(
        name = "UNITY AT KITKAT CLUB",
        venueName = "KitKatClub",
        venueAddress = """Köpenicker Straße 76
10179 Berlin""",
        latitude = 52.5038,
        longitude = 13.4241,
        startTime = "2026-05-28T22:00:00+02:00",
        endTime = "2026-05-29T07:00:00+02:00",
        lineup = listOf(
            Artist("Joe Shmoo LIVE b2b Balkhausen LIVE"),
            Artist("alemiko LIVE"),
            Artist("Limoncello")
        ),
        description = """EVERY FUCKING THURSDAY – UNITY AT KITKAT CLUB

UNITY is a sex-positive kinky community of free spirits who come together to connect body and mind in a respectful, sexy, and playful atmosphere.

Our clear focus lies on LIVE ACTS. Musically, UNITY spans a wide spectrum from uptempo house through trance and neotrance to driving Berlin techno.

No strict dress code, but "Dress for KitKat Club." Sex-positive safe space. Consent and respect are mandatory. No-photo policy for guests.""",
        url = "https://de.ra.co/events/2343617",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNzRhNjFjZTM1MmVkYTY3MDZhNTY4OTgwODQzODZjMzljM2E5Mzg4OS5qcGc=",
        price = 20.0 // kein Preis auf RA angegeben
    ),

    Event(
        name = "Amsterdam Techno Records and OXI on Thursday's",
        venueName = "OXI",
        venueAddress = """Wiesenweg 1-4
10365 Berlin""",
        latitude = 52.5048,
        longitude = 13.4749,
        startTime = "2026-05-28T23:00:00+02:00",
        endTime = "2026-05-29T07:00:00+02:00",
        lineup = listOf(
            Artist("gãl (SI)"), Artist("Gabrielle (DE)"), Artist("Solvados"),
            Artist("UniKhatu"), Artist("eXpressivo")
        ),
        description = """Amsterdam Techno Records and OXI on Thursday's [Amsterdam and Berlin]

"ATR is about the music. Our artists deliver pure, underground techno from Amsterdam & Berlin combined: Dark, Raw, Real. No filters, no cameras, massive cool venues, ravers, artists, sound and lights."

Hypnotic groove, hardgroove and RAW Techno. Inclusive line-up with top artists from Amsterdam and Berlin. Come and experience our vision of Techno.

No photo/video policy. Respect everyone's identity, act with consent and care.""",
        url = "https://de.ra.co/events/2416039",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZjc4M2QwNDgyZTdhYmYyYTI0Njk1YjMzZjY1MGJiNmEzZDc0Y2RiZi5qcGc=",
        price = 11.30 // Mindestpreis laut RA (€11,30 - €16,80)
    ),

    Event(
        name = "Else x KEYI Magazine",
        venueName = "Else",
        venueAddress = """An den Treptowers 10
12435 Berlin""",
        latitude = 52.4837,
        longitude = 13.4482,
        startTime = "2026-05-28T17:00:00+02:00",
        endTime = "2026-05-29T08:00:00+02:00",
        lineup = listOf(
            Artist("Ancient Methods"), Artist("Berlin Bunny b2b Ludmila Houben"),
            Artist("Bloody Mary"), Artist("CMR-A b2b Miro Von"), Artist("Casey Spooner"),
            Artist("Franz Scala b2b Eyesdice"), Artist("Icey Planet hybrid live"),
            Artist("Mar/us"), Artist("Shakti (KR) b2b Lokier")
        ),
        description = """Where is Else?
In the heart of East Berlin, right beside Treptower Park SBahn Station.

What if it rains?
Else's entire open-air dancefloor is covered by a transparent roof that will keep you warm and dry.

What's the sound system?
There's a brand new, high-end sound system: Lambda Labs QX3 and a new DJ booth, which is surrounded by the crowd from all sides.

When does the indoor room open?
After 22:00 'til the early hours.

Is there food?
Oven-baked pizza all-day.

Is there a chill-out space?
Hang out and have a drink at our riverside terrace.

What's the minimum age for entry?
21+

Do you take cash or card?
We only accept CARD payment throughout the club.

RA ticket does not guarantee entry; purchased tickets will be refunded in that case.""",
        url = "https://de.ra.co/events/2396157",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMGM3YTE0MjE3MjVjOWM2NmUwMGFmNTY3M2RiODgxMjdmN2I0YTA4My5qcGc=",
        price = 25.0
    ),

    Event(
        name = "STRAFF – Thursday Techno",
        venueName = "Der Weiße Hase",
        venueAddress = """Revaler Str. 99
10245 Berlin""",
        latitude = 52.5066,
        longitude = 13.4554,
        startTime = "2026-05-28T23:00:00+02:00",
        endTime = "2026-05-29T08:00:00+02:00",
        lineup = listOf(
            Artist("Kevin & Emilia Fink"), Artist("Emma (8)"), Artist("m.msen")
        ),
        description = """STRAFF steht für
straffen Techno, straffe Leute, straffe Drinks, straff fairer Eintritt.

5€ entry until 1 AM

@ Tempel: Techno.
@ Anne Bar: House & Grooves. Sitzen, chillen, Brause schlürfen.

18+ · Valid ID required.""",
        url = "https://de.ra.co/events/2420882",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZWQ5MGM3NzcyNjkwNzRkNDk0MzA4ZGFkMTI2MGUzMzdjMGM0MTcxMy5qcGc=",
        price = 5.0
    ),

    // ── Freitag, 29. Mai 2026 ────────────────────────────────────────────────

    Event(
        name = "Teletech Berlin",
        venueName = "DSTRKT Club Berlin",
        venueAddress = """Storkower Straße 123
10407 Berlin""",
        latitude = 52.5274,
        longitude = 13.4416,
        startTime = "2026-05-29T22:00:00+02:00",
        endTime = "2026-05-30T08:00:00+02:00",
        lineup = listOf(
            Artist("ACOR"), Artist("Amo (IT)"), Artist("DIKKE BAAP"), Artist("Dr. G"),
            Artist("GRAVEDGR"), Artist("KNTRLVRLST"), Artist("Lady Maru"), Artist("Lenny Dee"),
            Artist("Lola Cerise"), Artist("NVNS"), Artist("Razzle Dazzler"), Artist("Shanixx"),
            Artist("TNT (2)"), Artist("VIDO"), Artist("Yoshiko")
        ),
        description = "Teletech is BACK in Berlin with another full venue takeover of DSTRKT!",
        url = "https://de.ra.co/events/2378819",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNWJjYWVlYjc1MTIxYmJhYzczYWExNjllYjU5MzI5NGMyMzRjZjQ1ZS5qcGc=",
        price = 20.0
    ),

    Event(
        name = "NEUTRUM X INFERNO w/ GI.O, DJ Mischkonsum, Melanchromie, DJ Rundfunk, Treibende Kraft",
        venueName = "ÆDEN",
        venueAddress = """Schleusenufer 2
10997 Berlin""",
        latitude = 52.4993,
        longitude = 13.4108,
        startTime = "2026-05-29T23:00:00+02:00",
        endTime = "2026-05-30T08:00:00+02:00",
        lineup = listOf(
            Artist("bbymeister"), Artist("DJ Mischkonsum"), Artist("Treibende Kraft"),
            Artist("DJ Rundfunk"), Artist("Sonnenkind"), Artist("Elias Nuit (2)"),
            Artist("GM1 (IT)"), Artist("jeanska"), Artist("Kimberly b2b Baby V"),
            Artist("Melanchromie"), Artist("NYXEA")
        ),
        description = """The 3rd chapter unfolds: NEUTRUM X INFERNO join forces once again, this time at Aeden Club Berlin.

Two Berlin-based collectives, one shared vision: Pushing rave culture forward with relentless energy and a deep connection to the dancefloor.

Expect Bounce, Trance, Hardtrance and Hardtechno all night.

Third row. Same fire. New dimension.""",
        url = "https://de.ra.co/events/2382631",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNTQ4N2JkYTBmNWVjYmE2NzZiZTZkZTAwMTg3Y2Y1Y2I1YjI5ZGFmMS5wbmc=",
        price = 25.0 // kein Preis auf RA angegeben
    ),

    Event(
        name = "CYCLE (OUTDOOR + INDOOR) pres. Franky-B, Nachtigaller, Cargo, SPEEDBOYS & Trancestrudel",
        venueName = "Lokschuppen Berlin",
        venueAddress = """Revaler Straße 99 (Zugang über Warschauer Brücke)
10245 Berlin""",
        latitude = 52.5066,
        longitude = 13.4554,
        startTime = "2026-05-29T23:00:00+02:00",
        endTime = "2026-05-30T09:00:00+02:00",
        lineup = listOf(
            Artist("Franky-B"), Artist("Nachtigaller"), Artist("DTEXX"), Artist("SIKXTO"),
            Artist("Filialleiter"), Artist("CARGO (DE)"), Artist("Trancestrudel"),
            Artist("SPEEDBOYS"), Artist("MIMI404"), Artist("TechTonic b2b NØA (DE)"),
            Artist("Nils Primas"), Artist("Death of Mars")
        ),
        description = """CYCLE → FALL INTO THE LOOP

CYCLE returns to Berlin for chapter three. Born from the EUPHORIK movement and connected to the same community.

Two floors with a new setting.
Mainfloor: Trance/Bounce – outdoor
Secondfloor: Hardtechno/Schranz

Open air energy meets constant pressure. Two moods. One direction. Sound on repeat.

Zero tolerance for racism, sexism or GHB. Awareness team present throughout the night.

NO PHOTO NO VIDEO POLICY""",
        url = "https://de.ra.co/events/2331874",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNzRjNDMxMjAzODNlZGU4ZTk1MWJlYmY0ZmU3ODQwNGFhMTljOTA1Yi5wbmc=",
        price = 20.0 // kein Preis auf RA angegeben
    ),

    Event(
        name = "ASID x GHOSTROMANCE: 0mg! Absolute (Black Tar): Evian Christ, Felix Lee, HDMIRROR",
        venueName = "OXI",
        venueAddress = """Wiesenweg 1-4
10365 Berlin""",
        latitude = 52.5048,
        longitude = 13.4749,
        startTime = "2026-05-29T15:00:00+02:00",
        endTime = "2026-05-30T06:00:00+02:00",
        lineup = listOf(
            Artist("Evian Christ"), Artist("Felix Lee (dj)"), Artist("HDMIRROR (live)"),
            Artist("TRYCE"), Artist("Franarchy"), Artist("bod [包家巷]"), Artist("Babysouljia"),
            Artist("Aska (live & dj)"), Artist("Myen"), Artist("S280F"), Artist("blastah"),
            Artist("REBE"), Artist("Identity Theft + Swoopy (live)"), Artist("truthspeaker"),
            Artist("PAX (2) b2b CUNT REMEMBER"), Artist("Freestyler (2)"), Artist("Taylor Cherry"),
            Artist("Murder Club"), Artist("Xavior™"), Artist("LINNÉA"), Artist("Ghula (live)"),
            Artist("Locre b2b Comperi"), Artist("Forever United"), Artist("Metaphys1ca"),
            Artist("Lilboyblb (live) b2b Global b2b Coinman"),
            Artist("Trendsetter b2b 64 Meter"), Artist("Sugar Barbie"),
            Artist("Malice Doll + D.Blavatsky (live)"), Artist("Yura Chaim"),
            Artist("Secret Guest")
        ),
        description = """0mg! Absolute Black Tar Berlin Big Kill by ASID x Ghost Romance.
An Ultimate Trance Endeavor To Experience, Participation Is Voluntary, Impact Is Not.

3 FLOORS ++ GARDEN OPEN AIR
3PM – 6AM

Supported by Transatlantic Earth, Tarnac, Forever Unlimited and Tear City.""",
        url = "https://de.ra.co/events/2430216",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZTNmMzJjODE1MTYzMDBhYmViODRmOWJiOTk3NmU1NTM2Mjc5ZGU3OS5wbmc=",
        price = 25.0
    ),

    Event(
        name = "techtelmechtel w/ Greg Downey, Scot Project, SACHSENTRANCE, YOVA, DJ ritalino and many more",
        venueName = "://about blank",
        venueAddress = """Markgrafendamm 24c
10245 Berlin""",
        latitude = 52.5058,
        longitude = 13.4582,
        startTime = "2026-05-29T23:00:00+02:00",
        endTime = "2026-05-30T10:00:00+02:00",
        lineup = listOf(
            Artist("Greg Downey"), Artist("Scot Project"), Artist("RaverPik b2b Atreo b2b HKKPTR"),
            Artist("YOVA"), Artist("DJ ritalino b2b ch4r20tte"), Artist("Sioc"),
            Artist("Ravernunft"), Artist("Rasch (vinyl set)"), Artist("C150"),
            Artist("Haffenloher b2b Apexsion b2b Tobias Sommer"), Artist("Der Garvin")
        ),
        description = """16 acts, 3 floors, legends only!
Tech trance, psy & euphoric styles
29.05.26 - ://about blank - 23:00–10:00

techtelmechtel revives the original trance rave feeling: collective euphoria, a sweet crowd and a strong focus on musical quality.

MDF: Full pressure tech trance and pure uplift.
Lobby: Deep flow, psytrance and trance.
The Hut: Playful detour through house, tech house, disco and eurodance.

Anti-commercial collective of longtime trance fans. Positive, open, joyful and safe space. Dedicated awareness team present.

NO ANTISEMITISM / NO SEXISM / NO HOMO- OR TRANSPHOBIA / NO HATE / NO RACISM""",
        url = "https://de.ra.co/events/2392407",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZjEwMzhjZWM4YjM2NzRlZGEwOWVmODE2YWRkNTQyOGNlN2UzZDMyMy5qcGc=",
        price = 30.0 // kein Preis auf RA angegeben
    ),

    Event(
        name = "Tears of Joy - 1 Year Anniversary",
        venueName = "RSO.BERLIN",
        venueAddress = """Schnellerstraße 137
12439 Berlin""",
        latitude = 52.4615,
        longitude = 13.5010,
        startTime = "2026-05-29T23:00:00+02:00",
        endTime = "2026-05-30T09:00:00+02:00",
        lineup = listOf(
            Artist("Beau Didier"), Artist("Echoes Of October"), Artist("K. Luisa"),
            Artist("Marcus L"), Artist("Shanda")
        ),
        description = """One year after its launch, Berlin-based label Tears Of Joy returns to RSO Berlin to celebrate its first anniversary with the second official label night.

Founded by Echoes Of October, Tears Of Joy has quickly established itself as a platform for forward-thinking club music rooted in the energy of Berlin's underground.

Cash and cashless payment.

Via S-Bahn: 4 stops from Ostkreuz or 3 stops from Neukölln, then 5-minute walk from Schöneweide station.

Presale ticket does not assure admission.""",
        url = "https://de.ra.co/events/2392715",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNTE0YThmMDc5OWY1NDc5MGU3ZGI1YjJhM2MyMDA3MTFiYTU1YmE1MC5qcGc=",
        price = 25.0 // kein Preis auf RA angegeben
    ),

    Event(
        name = "STREETBEAT/4 - w. BIG TOE, BANGS, Sportler99, VATO, ALLSTARS & FREESTYLE",
        venueName = "ÆDEN",
        venueAddress = """Schleusenufer 2
10997 Berlin""",
        latitude = 52.4993,
        longitude = 13.4108,
        startTime = "2026-05-29T18:00:00+02:00",
        endTime = "2026-05-30T23:00:00+02:00",
        lineup = listOf(
            Artist("BIG TOE"), Artist("BANGS"), Artist("SPORTLER99"),
            Artist("VATO"), Artist("SCHIEBERGRILLZBANDE"), Artist("CARLITO51"),
            Artist("ALLSTARS"), Artist("FREESTYLE CONTEST")
        ),
        description = """STREETBEAT IS BACK. Mit einem ehrwürdigem Lineup bringen wir euch Rap mit Authentizität und Liebe zurück auf die Bühne! Mit KONZERT und FREESTYLE CONTEST zeigen uns Talente aus Berlin und Hamburg, was es heißt, das Mic zum glühen zu bringen.

Seid dabei für diesen unvergesslichen Abend!

18+""",
        url = "https://de.ra.co/events/2382679",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNjNlZTc1ZTFmMmFkNDA1MWRlODYwYjZlNmFmMDc0YTU2MDlhY2Y2Ni5wbmc=",
        price = 15.0 // kein Preis auf RA angegeben
    ),

    // ── Samstag, 30. Mai 2026 ────────────────────────────────────────────────

    Event(
        name = "CONTRA: Saturday",
        venueName = "Kraftwerk Berlin",
        venueAddress = """Köpenicker Str. 70
10179 Berlin""",
        latitude = 52.5033,
        longitude = 13.4218,
        startTime = "2026-05-30T17:30:00+02:00",
        endTime = "2026-05-31T08:00:00+02:00",
        lineup = listOf(
            Artist("Blawan"), Artist("Knock2"),
            Artist("Nai Barghouti ft. THYS (2) & Péter Somos"), Artist("Skrillex"),
            Artist("ABADIR & HOGIR"), Artist("Bill Kouligas"), Artist("Binghi"),
            Artist("Devon Rexi meets John T. Gast"), Artist("Dj Babatr b2b Dj Deep RH"),
            Artist("Dj Bassan"), Artist("DJ LAG"), Artist("Flowdan"), Artist("GЯEG"),
            Artist("Hamdi"), Artist("ISOxo"), Artist("Juliana Huxtable"),
            Artist("Kanucia b2b Mobilegirl b2b Nico Adomako"), Artist("Khadija Al Hanafi"),
            Artist("KOLLIN"), Artist("MONEYAMA"), Artist("nunguja"), Artist("Rafush"),
            Artist("RHR b2b Nick León"), Artist("Sarz"), Artist("Slikback"),
            Artist("Slim Soledad b2b ???"), Artist("Tatyana Jane"), Artist("Thys"),
            Artist("TYGAPAW b2b CEM"), Artist("upsammy & Valentina Magaletti"),
            Artist("Wallis")
        ),
        description = """CONTRA is an evolving platform exploring productive collisions between artists, scenes, & audiences across contemporary music culture – developed collaboratively by Skrillex & an international curatorial & creative team.

CONTRA begins with a two-day takeover of Kraftwerk Berlin (30–31 May), activating the vast former power plant & all of its club spaces Tresor, Globus & OHM – in collaboration with Berlin Atonal.

Environmental design led by Paris studio Matière Noire. 70+ artists spanning club music, experimental sound & live performance.

Low Income Tickets: €35 for single-night access / €60 full weekend pass (apply via email).""",
        url = "https://de.ra.co/events/2440259",
        flyerURL = "https://public.ra.co/images/ra-co-logo.jpeg",
        price = 35.0 // Low-income Mindestpreis; reguläre Tickets höher
    ),

    Event(
        name = "Power Dance Club XXL [24hours]",
        venueName = "KREUZWERK",
        venueAddress = """Lobeckstraße 30-35
10969 Berlin""",
        latitude = 52.4990,
        longitude = 13.4065,
        startTime = "2026-05-30T23:59:00+02:00",
        endTime = "2026-05-31T23:59:00+02:00",
        lineup = listOf(
            Artist("131bpm"), Artist("Aldonna"), Artist("CEM"), Artist("Chris Cruse"),
            Artist("Fafi Abdel Nour"), Artist("Jennifer Loveless"), Artist("Luigi Di Venere"),
            Artist("Maria Politi"), Artist("Marie Malarie"), Artist("Maze DJ"),
            Artist("Revivis"), Artist("Shay Malt"), Artist("Skankstasy"),
            Artist("source:link"), Artist("SPRKLBB"), Artist("Stathis (GR)"), Artist("ZANNT")
        ),
        description = """XXL Edition - 24 hours

3 Dance Floors + Open Air Garden Opening + extra play rooms

Expect the usual steamy dance floors ;)

Please note that a presale ticket does not guarantee entry. The club reserves the right to deny entry. 19+""",
        url = "https://de.ra.co/events/2308625",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vOTVlYmVjMDU3NmViNTQ3YzZjYmUyNzYzOWRkNzNmYmI1ODdmZThiNi5qcGc=",
        price = 18.0 // Mindestpreis (18 / 25 / 27)
    ),

    Event(
        name = "Chris Luno – Daytime Open Air",
        venueName = "SAGE",
        venueAddress = """Köpenicker Str. 18-20
10997 Berlin""",
        latitude = 52.5005,
        longitude = 13.4141,
        startTime = "2026-05-30T15:00:00+02:00",
        endTime = "2026-05-31T03:00:00+02:00",
        lineup = listOf(
            Artist("Chris Luno"), Artist("Aliska b2b Basic Instinct"), Artist("Aimé You"),
            Artist("DOS b2b Marius Holm"), Artist("Ede b2b KENZA KAYATI"),
            Artist("Janina"), Artist("Mona Yim"), Artist("Robin Nicolas")
        ),
        description = """Summer is almost here and Chris Luno is back at SAGE Berlin for a day and night experience on three dancefloors!

After last year's unforgettable edition with over 2k dancers in the sand and over 900K watching online, Chris returns with his signature uplifting house sound to the iconic beach dancefloor.

Presented by Watergate and Noire.

Two floors + aftershow. Day to night. Food & drinks on site. Fully covered areas in case of rain.

Tickets available at the door (cash or card). No discrimination of any kind, ever.""",
        url = "https://de.ra.co/events/2378722",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vM2FiMDY2ZTM4ZWQ2NjkzNzM5MzIxZDQxMDk0YjVlNTBiNDUyMDIwMy5wbmc=",
        price = 25.0 // kein Preis auf RA angegeben; Tickets an der Abendkasse
    ),

    Event(
        name = "An der frischen Luft / 10. Jubiläum",
        venueName = "Strandbad Erkner",
        venueAddress = """Zum Freibad
15537 Erkner""",
        latitude = 52.4234,
        longitude = 13.7547,
        startTime = "2026-05-30T12:00:00+02:00",
        endTime = "2026-05-30T23:00:00+02:00",
        lineup = listOf(
            Artist("L.zwo"), Artist("Mark Dekoda"), Artist("Phaxe"), Artist("Burn in Noise"),
            Artist("Anuuk"), Artist("DonChoppa"), Artist("SEKTOR69"), Artist("Grace Thompson"),
            Artist("Michael Klotz"), Artist("Paul Wolf"), Artist("BENITO (DE)"),
            Artist("Djingis Kahn"), Artist("Daora"), Artist("Texo"), Artist("RHYTMOX"),
            Artist("Till Krimsen"), Artist("Yves Meyer"), Artist("monervo"),
            Artist("MØABEAT"), Artist("theis.de"), Artist("NYXEA")
        ),
        description = """10 JAHRE AN DER FRISCHEN LUFT – DAS SOMMERFESTIVAL IM STRANDBAD ERKNER. 30+ Artists!

Wir feiern 10 Jahre An der frischen Luft – ein ganzes Jahrzehnt voller Sonne, Musik und unvergesslicher Momente! Seit 2016 steht unser Open-Air für echte Sommer-Vibes, treibende Beats und eine Community.

Highlights:
- Goagarden-Floor
- Technobus-Floor
- Mainfloor mit internationalen Acts
- Chillout, Food & Drinks direkt am See
- Kunstinstallationen

10 Jahre. 3 Floors. 1 Family.""",
        url = "https://de.ra.co/events/2305820",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vYTVmNmU2YmQ5M2RiOGQwZDJmYWI5ZTY4ODE0MDI0NWRjMzk1YWEyYy5wbmc=",
        price = 15.0 // Preis auf RA nur als "€" ohne Betrag angegeben
    ),

    Event(
        name = "Else x Toy Tonics",
        venueName = "Else",
        venueAddress = """An den Treptowers 10
12435 Berlin""",
        latitude = 52.4837,
        longitude = 13.4482,
        startTime = "2026-05-30T14:00:00+02:00",
        endTime = "2026-05-31T08:00:00+02:00",
        lineup = listOf(
            Artist("Davide Dev"), Artist("FIMIANI"), Artist("geneva"),
            Artist("Kapote"), Artist("Sam Ruffillo"), Artist("Sofiia Zoloto")
        ),
        description = """Where is Else?
In the heart of East Berlin, right beside Treptower Park SBahn Station.

What if it rains?
Else's entire open-air dancefloor is covered by a transparent roof that will keep you warm and dry.

What's the sound system?
There's a brand new, high-end sound system: Lambda Labs QX3 and a new DJ booth, which is surrounded by the crowd from all sides.

When does the indoor room open?
After 22:00 'til the early hours.

Is there food?
Oven-baked pizza all-day.

What's the minimum age for entry?
21+

We only accept CARD payment throughout the club.
RA ticket does not guarantee entry; purchased tickets will be refunded in that case.""",
        url = "https://de.ra.co/events/2385749",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNGFjMjQzYzhmNTlmYjQ2NDc1Yjg3ZTk2Y2YwYzk5NTIxODJlZDMxZi5wbmc=",
        price = 25.0 // kein Preis auf RA angegeben
    ),

    Event(
        name = "XTRUDE x ANTEA w/ Stef Mendesidis, Len Faki, Tommy Four Seven, Jessie Granqvist and Jasmin",
        venueName = "RSO.BERLIN",
        venueAddress = """Schnellerstraße 137
12439 Berlin""",
        latitude = 52.4615,
        longitude = 13.5010,
        startTime = "2026-05-30T23:59:00+02:00",
        endTime = "2026-06-01T08:00:00+02:00",
        lineup = listOf(
            // ROBUS floor (hosted by XTRUDE)
            Artist("Costanza"), Artist("Jessie Granqvist"), Artist("Kancheli"),
            Artist("Len Faki"), Artist("Shimmy Robin"), Artist("Stef Mendesidis live"),
            // SUMME floor (hosted by XTRUDE)
            Artist("Hervé"), Artist("Jasmín"), Artist("Sam Goku"),
            // OPAN floor (hosted by Antea)
            Artist("Hemka"), Artist("Regent"), Artist("Shrff"), Artist("Tommy Four Seven")
        ),
        description = """XTRUDE x ANTEA at RSO.BERLIN

ROBUS floor hosted by XTRUDE: Stef Mendesidis live, Len Faki, Jessie Granqvist and more.
SUMME floor hosted by XTRUDE: Jasmín, Hervé, Sam Goku.
OPAN floor hosted by Antea: Tommy Four Seven, Hemka and more.

Cash and cashless payment.

Via S-Bahn: 4 stops from Ostkreuz or 3 stops from Neukölln, then 5-minute walk from Schöneweide station.

Presale ticket does not assure admission.""",
        url = "https://de.ra.co/events/2413649",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vYTg0YTg5OTg0NmQ1NWYzZDMzMTY3Y2RhZjc4YWU3NTA5OTA4OGJmZS5qcGc=",
        price = 25.0 // kein Preis auf RA angegeben
    ),

    Event(
        name = "Devoted w. BYØRN, Kø:lab, Cara Elizabeth, Part Time Killer, DJ SPORTSCHUH, Danilo Filipe",
        venueName = "OST",
        venueAddress = """Alt-Stralau 1-2
10245 Berlin""",
        latitude = 52.5012,
        longitude = 13.4636,
        startTime = "2026-05-30T23:00:00+02:00",
        endTime = "2026-05-31T09:00:00+02:00",
        lineup = listOf(
            Artist("25EMEHEURE"), Artist("Athina"), Artist("BYØRN"),
            Artist("Cara Elizabeth b2b Part Time Killer"), Artist("Danilo Filipe"),
            Artist("DJ SPORTSCHUH"), Artist("Kø:lab"), Artist("Nadia Bel Air"),
            Artist("SEKTOR69")
        ),
        description = """Hardtechno / Hardtrance / Groove / Trance

Get ready for a full night of pure energy and heavy sounds with DEVOTED.
From Hardtechno to Trance and Hardtrance — this night is built for everyone who lives for the music.

Two floors, different atmospheres, one mission: dance until sunrise.

Club OST is cashless. No Photo/Video Policy.""",
        url = "https://de.ra.co/events/2356109",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMzg0ODY5MWNhNjExNWYzMzE2NTFkZGVhYzBjNDAwYjg3ZWI0NTFjNi5wbmc=",
        price = 30.0 // kein Preis auf RA angegeben
    ),

    Event(
        name = "GOAT BALL",
        venueName = "Lokschuppen Berlin",
        venueAddress = """Revaler Straße 99 (Zugang über Warschauer Brücke)
10245 Berlin""",
        latitude = 52.5066,
        longitude = 13.4554,
        startTime = "2026-05-30T23:00:00+02:00",
        endTime = "2026-05-31T06:00:00+02:00",
        lineup = listOf(
            Artist("Cobb Douglas"), Artist("CRITICAL ERROR 404"), Artist("DJ DRECKISCH"),
            Artist("DJ Primitivo"), Artist("DJ Tallboy"), Artist("ELOISA"),
            Artist("Johænsson"), Artist("Kamäleon"), Artist("Limoncello"),
            Artist("MRGNSTRN"), Artist("Ozzwald"), Artist("SOHOE"),
            Artist("Stinny Stone"), Artist("SZG")
        ),
        description = """GOAT BALL presents: PLAYBOY MANSION!!

Get access to the hottest spot and the hottest party in Berlin!
Get ready for the most playful night of the year… where nothing is too much and everything is a vibe!

Across 3 floors, the hottest DJs will serve you every tune you need for an unforgettable party night!

Presale tickets do not guarantee entry.""",
        url = "https://de.ra.co/events/2335752",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZTY2YWE0OWI2MTA1ZjkwMDVlNzVhNDk2YmE0OGIyM2NjNDY2OWM1MS5wbmc=",
        price = 20.0 // kein Preis auf RA angegeben
    ),

    Event(
        name = "Pikante [31 HOURS] + SUNDAY openair KDW market",
        venueName = "ÆDEN",
        venueAddress = """Schleusenufer 2
10997 Berlin""",
        latitude = 52.4993,
        longitude = 13.4108,
        startTime = "2026-05-30T23:00:00+02:00",
        endTime = "2026-06-02T06:00:00+02:00",
        lineup = listOf(
            Artist("BIXBITA"), Artist("FJUSHA"), Artist("SATYS FYRE"), Artist("GI.O"),
            Artist("4NOUK"), Artist("Amo (IT)"), Artist("August Kind"), Artist("Benua"),
            Artist("Bruno Brero"), Artist("cell1"), Artist("DJ ANGEL (fr)"), Artist("DDUCATI"),
            Artist("DJ.Egoshooter10000"), Artist("Deltapeak"), Artist("DJ HINZ"),
            Artist("DJ HOTMAIL"), Artist("Dollushka"), Artist("elfie"), Artist("Fonick"),
            Artist("GM1 (IT)"), Artist("goddamnmara"), Artist("Hanne B"), Artist("honeyboy"),
            Artist("Iguana (2)"), Artist("Impulsive Behaviour"), Artist("Kizu"),
            Artist("Krash Cora"), Artist("Mefteh"), Artist("Shake Daddy"),
            Artist("Slimegoat144"), Artist("søsa"), Artist("The Kiss"), Artist("YOVA"),
            Artist("ZAHNATZIN"), Artist("ZELIA")
        ),
        description = """31-hour Pikante marathon for our 2 Year Anniversary!

Starting from Saturday night until Monday morning, non-stop music, workshops and a creators market hosted by Kinder Der Welt during Sunday daytime.

SUNDAY OPENAIR KINDER DER WELT CREATOR'S MARKET FROM 2PM TO 9PM.

You are not ready for this!""",
        url = "https://de.ra.co/events/2389847",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNDBmY2MwNDc2ZjYwYzVmNzc0OWU1MjdmNmMxNDY1OTQyYTAxNDJjYS5wbmc=",
        price = 15.0 // kein Preis auf RA angegeben
    ),

    Event(
        name = "Pretty Pink (Open Air) - free entry until 7pm",
        venueName = "Ritter Butzke",
        venueAddress = """Ritterstraße 26
10969 Berlin""",
        latitude = 52.4996,
        longitude = 13.4052,
        startTime = "2026-05-30T18:00:00+02:00",
        endTime = "2026-05-31T07:00:00+02:00",
        lineup = listOf(
            Artist("Pretty Pink"), Artist("Bebetta"), Artist("Vlad Yaki"),
            Artist("Bonnie Spacey"), Artist("Max Soler")
        ),
        description = "Open Air + indoor event with Pretty Pink, Bebetta, Vlad Yaki, Bonnie Spacey and Max Soler at Ritter Butzke. Free entry until 7pm.",
        url = "https://de.ra.co/events/2400364",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNzI1NGQ3NDcyYTE1ZDA0NTI5NjBmMmU0ZmJhOTkxNWQ1M2E2YWY0MS5qcGc=",
        price = 0.0 // Free entry until 7pm, Ticketpreis danach nicht auf RA angegeben
    ),

    Event(
        name = "CONNECT x 23Degrees: DAY & NIGHT PARTY w/ Osmosis Jones, ESC, n4tee and many more",
        venueName = "OXI",
        venueAddress = """Wiesenweg 1-4
10365 Berlin""",
        latitude = 52.5048,
        longitude = 13.4749,
        startTime = "2026-05-30T14:00:00+02:00",
        endTime = "2026-05-31T08:00:00+02:00",
        lineup = listOf(
            Artist("Osmosis Jones"), Artist("ESC (5)"), Artist("n4tee"),
            Artist("DJ NORTHERN"), Artist("Jana Falcon"), Artist("yungfya"),
            Artist("Lizzle"), Artist("fbi (1) b2b KVLR (System Alba)"),
            Artist("Vuhri"), Artist("SNC Crew"), Artist("3Hertz")
        ),
        description = """CONNECT returns with its biggest edition yet, joined by UK-based artist agency 23degrees.

May 30th at OXI: very first DAY & NIGHT event.
OXI Garten from 14:00–22:00: house, tech house, and UKG open-air.
Night: bass-heavy and broken rhythms indoors.

18 hours of carefully curated music, wholesome community, top-tier vibes.

Awareness by Queer Mama. Information booth "Safer Nightlife" by SONAR.
Night Ticket upgradeable to Day & Night for 5€ at the Door.""",
        url = "https://de.ra.co/events/2416108",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vODJiZmUwNGNiYTgwNzdiYzkzODBkMjQ5NDNjMTNjYmY4ZDJhMjc5Mi5qcGc=",
        price = 25.0
    ),

    // ── Sonntag, 31. Mai 2026 ────────────────────────────────────────────────

    Event(
        name = "Else x Alarico pres. FLIRT",
        venueName = "Else",
        venueAddress = """An den Treptowers 10
12435 Berlin""",
        latitude = 52.4837,
        longitude = 13.4482,
        startTime = "2026-05-31T14:00:00+02:00",
        endTime = "2026-06-01T06:00:00+02:00",
        lineup = listOf(
            Artist("Alarico All Day Long"),
            Artist("FENIM0RE All Night Long")
        ),
        description = """Where is Else?
In the heart of East Berlin, right beside Treptower Park SBahn Station.

What if it rains?
Else's entire open-air dancefloor is covered by a transparent roof that will keep you warm and dry.

What's the sound system?
There's a brand new, high-end sound system: Lambda Labs QX3 and a new DJ booth, which is surrounded by the crowd from all sides.

When does the indoor room open?
After 22:00 'til the early hours.

Is there food?
Oven-baked pizza all-day.

Is there a chill-out space?
Hang out and have a drink at our riverside terrace.

What's the minimum age for entry?
21+

Do you take cash or card?
We only accept CARD payment throughout the club.

RA ticket does not guarantee entry; purchased tickets will be refunded in that case.""",
        url = "https://de.ra.co/events/2386886",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZjhmNTY0YzA2ZWYwYjEzMzAxMTFjOGEyYTQ0MDQ5ZWEyYmQxM2FmNi5wbmc=",
        price = 25.0
    ),

    Event(
        name = "Else Opening x Floorplan",
        venueName = "Else",
        venueAddress = """Alt-Treptow 11
12435 Berlin""",
        latitude = 52.4837,
        longitude = 13.4482,
        startTime = "2026-06-24T16:00:00+02:00",
        endTime = "2026-06-25T08:00:00+02:00",
        lineup = listOf(
            Artist("CRYME"), Artist("ESVEAE"), Artist("Floorplan (Robert Hood & Lyric Hood)"),
            Artist("Handmade b2b Stella Zekri"), Artist("Natalie Robinson"), Artist("Power Squad")
        ),
        description = """Where is Else?
In the heart of East Berlin, right beside Treptower Park SBahn Station.
What if it rains?
Else's entire open-air dancefloor is covered by a transparent roof that will keep you warm and dry.
What's the sound system?
There's a brand new, high-end sound system: Lambda Labs QX3 and a new DJ booth, which is surrounded by the crowd from all sides.
When does the indoor room open?
After 22:00 'til the early hours.
Is there food?
Oven-baked pizza all-day.
Is there a chill-out space?
Hang out and have a drink at our riverside terrace.
What's the minimum age for entry?
21+
Do you take cash or card?
We only accept CARD payment throughout the club.
Where can I buy a ticket?
Tickets are available on RA or at the door with a CARD payment.
RA ticket does not guarantee entry; purchased tickets will be refunded in that case.""",
        url = "https://de.ra.co/events/2385678m",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZDQ1MDE5YzQwYWQyNGY0YjU0YmU3YWY3NGQzYzAyYThmNGVjYzU0ZC5qcGc=",
        price = 15.0
    ),
    // ── 24. April 2026 ──────────────────────────────────────────────────────

    Event(
        name = "Else Opening x Floorplan",
        venueName = "Else",
        venueAddress = """Alt-Treptow 11
12435 Berlin""",
        latitude = 52.4837,
        longitude = 13.4482,
        startTime = "2026-04-24T16:00:00+02:00",
        endTime = "2026-04-25T08:00:00+02:00",
        lineup = listOf(
            Artist("CRYME"), Artist("ESVEAE"), Artist("Floorplan (Robert Hood & Lyric Hood)"),
            Artist("Handmade b2b Stella Zekri"), Artist("Natalie Robinson"), Artist("Power Squad")
        ),
        description = """Where is Else?
In the heart of East Berlin, right beside Treptower Park SBahn Station.
What if it rains?
Else's entire open-air dancefloor is covered by a transparent roof that will keep you warm and dry.
What's the sound system?
There's a brand new, high-end sound system: Lambda Labs QX3 and a new DJ booth, which is surrounded by the crowd from all sides.
When does the indoor room open?
After 22:00 'til the early hours.
Is there food?
Oven-baked pizza all-day.
Is there a chill-out space?
Hang out and have a drink at our riverside terrace.
What's the minimum age for entry?
21+
Do you take cash or card?
We only accept CARD payment throughout the club.
Where can I buy a ticket?
Tickets are available on RA or at the door with a CARD payment.
RA ticket does not guarantee entry; purchased tickets will be refunded in that case.""",
        url = "https://de.ra.co/events/2385678",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZDQ1MDE5YzQwYWQyNGY0YjU0YmU3YWY3NGQzYzAyYThmNGVjYzU0ZC5qcGc=",
        price = 15.0
    ),
    Event(
        name = "Queer Night Market: Drag & Live Performances",
        venueName = "Holzmarkt 25",
        venueAddress = """Holzmarktstraße 25
10243 Berlin""",
        latitude = 52.5130,
        longitude = 13.4229,
        startTime = "2026-04-24T16:00:00+02:00",
        endTime = "2026-04-24T23:00:00+02:00",
        lineup = listOf(
            Artist("Queer & Pose - Interactive Life Drawing Workshop (4PM-6PM)"),
            Artist("@houseofkum Collective - Live Drag Shows & Performances (6:30PM-9:30PM)")
        ),
        description = """That Gay Creation presents Berlin's Biggest Queer Creative Playground.
Queer Night Market Berlin lands at Holzmarkt 25 on April 24th for a one-night celebration of LGBTQ+ creativity, nightlife culture, and community connection in the heart of Berlin.
This in-person queer market and social event brings together independent artists, designers, makers, and performers alongside live drag, workshops, tattoo artists, street food, and interactive experiences. Expect a vibrant mix of queer culture, creative expression, and inclusive nightlife energy in one of Berlin's most unique cultural venues.
Whether you're searching for queer events in Berlin, LGBTQ+ nightlife, alternative markets, creative workshops, or spaces to connect with the community and allies, our Queer Night Market offers a welcoming environment built on visibility, expression, and collaboration.
Perfect for locals, visitors, creatives, and anyone exploring Berlin's queer scene.
First 100 guests receive free entry.
24 April
16:00 - 23:00
Holzmarkt Berlin
----
Limited Queer Night Market Goodie Bags
Only 300 available.
Each goodie bag includes priority entry to the event, allowing you to walk straight in without queuing, plus our "Tote-ally Queer" tote filled with curated products, treats, and surprises from selected brand partners.
Total value 40€+.
Available online in advance or on the day while stocks last.
Find That Gay creation's booth to redeem.""",
        url = "https://de.ra.co/events/2368136",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMWJiYWQ4MzA1Y2QyMmJkOTg5YzEzM2Y1MDZjMDU4MTNjNTFiYjU0Mi5wbmc=",
        price = 10.0
    ),
    Event(
        name = "ONYX with Ueberrest",
        venueName = "OST",
        venueAddress = """Revaler Straße 99
10245 Berlin""",
        latitude = 52.5012,
        longitude = 13.4636,
        startTime = "2026-04-24T23:00:00+02:00",
        endTime = "2026-04-25T09:00:00+02:00",
        lineup = listOf(
            Artist("Ueberrest"), Artist("JOKESONYOU"), Artist("DICE b2b GEORGE aka DR.RADSPORT"),
            Artist("DJ Sonnenbrand b2b DJ WASSERFALL"), Artist("KARISH"), Artist("Kamäleon"),
            Artist("multivitaminmarie"), Artist("NIA (4)")
        ),
        description = "",
        url = "https://de.ra.co/events/2227687",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZGI0ZmY5MTY4NTI2OWVkNTMxZjMxMTg4Yjk3ZmJmYzI4MWY1MzUyYS5qcGc=",
        price = 12.0
    ),
    Event(
        name = "SEKT? ZEHN, BLANK! - 16th ://about blank birthday",
        venueName = "://about blank",
        venueAddress = """Markgrafendamm 24c
10245 Berlin""",
        latitude = 52.5058,
        longitude = 13.4582,
        startTime = "2026-04-24T20:00:00+02:00",
        endTime = "2026-04-27T05:00:00+02:00",
        lineup = listOf(
            Artist("Alex.Do (://about blank)"), Artist("Anja Schneider (Sousmusic)"),
            Artist("a:tok b2b THNTS (://about blank)"), Artist("Barbara Hofmann (://about blank)"),
            Artist("Cirkle live (sk_eleven / token)"), Artist("Disco Destiny (wünsch.dir.was)"),
            Artist("DJ Buona Sara"), Artist("Doudou MD (Slapfunk Records)"), Artist("Epedemi.c (://about crew)"),
            Artist("Eva (://about crew) b2b Marcel Koar (Chimaera)"), Artist("fr. JPLA (://about blank)"),
            Artist("Hang Aoki (://about blank)"), Artist("Hanna Baertig (://about blank) b2b Sedaction (e.p.i.q.)"),
            Artist("Irakli (staub)"), Artist("Jessamine (://about blank)"), Artist("Josefina Tapia (Easy Latino Rec)"),
            Artist("Kwaint (://about blank)"), Artist("Lena Brumby (://about blank)"), Artist("Nadine Talakovics (staub)"),
            Artist("Nina Noir (Turnland Records)"), Artist("ogtrues (Art for Industry)"), Artist("painzain (Pain Records)"),
            Artist("Perel (Kompakt)"), Artist("Ponygirl (Different Sound)"), Artist("Ricardo Garduno (Illegal Alien)"),
            Artist("Rodmin (://about blank)"), Artist("Rosa Kante"), Artist("SIMA (Azur Collective)"),
            Artist("STASIC (://about crew)"), Artist("Stornoline (://about crew) b2b Kluntje (friz)"),
            Artist("Tactile (Trips)"), Artist("Toscan Haas (Spectrum Wave)"), Artist("upkar b2b jacopo (://about crew)"),
            Artist("Vy Tran b2b Mika Siponen b2b Ali E. (Humble Encounters)"),
            Artist("Menzel (Nihil) [Beachfloor]"), Artist("goldie (2) b2b Multifun (Objekt klein a & Mltmx rec.) [Beachfloor]"),
            Artist("Toni Pfad (Biotobt) [Beachfloor]"), Artist("Schorli b2b CHOREOPHILA (Juicy Culture) [Beachfloor]"),
            Artist("BBetriebswirt (mitmischen) [Beachfloor]"), Artist("AENZO [passiv aggressiv floor]"),
            Artist("K1KO [passiv aggressiv floor]"), Artist("Lemmy Winks [passiv aggressiv floor]"),
            Artist("MAARTEN [passiv aggressiv floor]"), Artist("Marcie (2) [passiv aggressiv floor]"),
            Artist("Die Julias (live)"), Artist("Der Assistent (live)"),
            Artist("KnarfMitte KillRellom feat. Tillamanda & Torben Wesche (live)"),
            Artist("OK Nein (live)"), Artist("Ponys auf Pump (live)"), Artist("Stefanie Schrank (live)"),
            Artist("Theilen (live)"), Artist("Rattenchor (Chor)"), Artist("Krawallkehlchen (://blankies) (Chor)")
        ),
        description = """seit mindestens sektzehn jahren schenken wir am letzten aprilwochenende aus anlass unseres clubgeburtstags reichlich eisgekuehlten schaumwein aus. freitagsueber warmraven mit den kids, anschliessend fundsachendingdong, eingeschworene choere und revival of the punkfluegel, und ab mitternacht techno, house und alles dazwischen und ausserhalb. samstag open-air festival mit beachfloor und gartenkonzerten, sonntag dann endlich die saisoneröffnung des gartendancefloors mit allen schikanen und bambule. so soll es sein, so wird es sein. aller guten dinge sind sekt.
NOTORISCH BLANK
"a und b und beides und keins // wir sind mehr und wir sind eins // alles schwankt und pendelt und kreist // morphing all the time // morgen sind wir wie neu" (stefanie schrank, shapeshifter) zum ungefaehr sechzehnten geburtstag glauben wir an alles und an jede, an alles gleichzeitig oder etwas besonderes. es ist moeglicherweise ohnehin immer alles das gleiche oder ganz sicher schon mal hiergewesen: das eingeubte und ausgereifte, das ungestume und eingetruebte, das ausufernde und ueberbordende, das ungebremsste und enthemmte, das unausgesprochene und entrueckte - und die nebuloese grundsubstanz fuer all das, was uns glueckte. sechzehn fluechtige jahre blankbridging the generation gap von x bis zero im spagat zwischen begegnungs- und bewegungsort, safer space und ballerplace, nachtasyl und haltestelle, technohoelle und housekapelle. wie lange haelt sie noch, die nonbinaere dreierbeziehung aus emanzipation, einheitslohn und eskapismus gegen ausschluesse, ausbeutung und autoritarismus? wie weiter balancieren zwischen artifizieller intellektualitaet und rueckwaertssuche, change management und gasterfahrung, adoloszenten vibes und postanalogen vibrationen, cashless wisper und blankless fears? und stippstoppen wir sie noch, die letzten zehn, fuenfzehn meter bis zum blankdown mit der autobahn, jener ewigen wunschgegnerin aus dem bewegungsautonomen playbook? challenge unacceptable. wir bleiben grobmotorisch blank. tl, dr ://about. weiter. machen. trotz "aussichtsloser lage" und "wegen immer noch sozialer frage und zentralperspektive" (blumfeld, aus den kriegstagebüchern).
AK Tagesticket: 25 EUR
AK Wochenendticket: 35 EUR
Ein Upgrade vom Tagesticket auf das Wochenendticket ist vor Ort moeglich.""",
        url = "https://de.ra.co/events/2350950",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vYjMyMWI5YTY4Nzg0MTZhYWM4ZmY3OTJmMWNhYzNhNWJiNTg5ZGRhMC5wbmc=",
        price = 25.0
    ),
    Event(
        name = "ONE NIGHT WITH / Tiefundton",
        venueName = "M-BIA",
        venueAddress = """Revaler Straße 99
10245 Berlin""",
        latitude = 52.5220,
        longitude = 13.4065,
        startTime = "2026-04-24T23:00:00+02:00",
        endTime = "2026-04-25T08:00:00+02:00",
        lineup = listOf(
            Artist("Tiefundton"), Artist("Kotti D'Azur"), Artist("Till Krimsen"), Artist("NYXEA"),
            Artist("Verkorxxt"), Artist("Daora"), Artist("Sascha Lindner")
        ),
        description = """ONE NIGHT WITH TIEFUNDTON
Am 24.04.2026 laedt der M-Bia Club Berlin zu einer besonderen Clubnacht ein.
Im Mittelpunkt steht Tiefundton, der mit seinem charakteristischen Sound zwischen treibendem Techno und tiefen, hypnotischen Grooves den Floor praegen wird.
Begleitet wird der Abend von erstklassigen Support Acts, die das Line-up abrunden und fuer eine durchgehend starke musikalische Reise sorgen.
Fokus auf Musik, Energie und Clubatmosphaere - ohne Schnickschnack.
24.04.2026
M-Bia Club Berlin
Weitere Artists werden in Kuerze bekannt gegeben.""",
        url = "https://de.ra.co/events/2334843",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMDMwYmFkNzRiOGU3NTdlZjA3NDBmYmNmNmFlMzVmN2IzMjNlNTE2ZS5wbmc=",
        price = 12.0
    ),
    Event(
        name = "Cybersex Invites: PETERBLUE, Bella Claxton, KIM SWIM",
        venueName = "AEDEN",
        venueAddress = """Revaler Straße 99
10245 Berlin""",
        latitude = 52.5003,
        longitude = 13.4232,
        startTime = "2026-04-24T23:00:00+02:00",
        endTime = "2026-04-25T09:00:00+02:00",
        lineup = listOf(
            Artist("Bella Claxton"), Artist("Charlie."), Artist("Cybersex"), Artist("Danny Wabbit"), Artist("FORTUNATA"),
            Artist("Herton"), Artist("KIM SWIM"), Artist("Lorcan Kelly"), Artist("Spacer Woman"), Artist("PETERBLUE"),
            Artist("The Muffin Man")
        ),
        description = """Cybersex are bringing their friends and peers to Aeden for a 2 floor takeover of the Berlin institution.
a-z
BELLA CLAXTON
CHARLIE.
CYBERSEX
DANNY WABBIT
FORTUNATA
HERTON
KIM SWIM
LORCAN KELLY
SPACER WOMAN
PETERBLUE
THE MUFFIN MAN
Presented by Teletech.""",
        url = "https://de.ra.co/events/2356819",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZTE5MDBhZjU1MDIwNzAzMmI3NjJjMjMyMzZiMGZhYjg2NTIyYWIzNi5qcGc=",
        price = 13.0
    ),
    Event(
        name = "Selected with Part Time Killer, Luxi Villar, Zorza, CAIVA B2B Frederic",
        venueName = "RSO.BERLIN",
        venueAddress = """Richard-Sorge-Straße 25a
10249 Berlin""",
        latitude = 52.4662,
        longitude = 13.5038,
        startTime = "2026-04-24T23:00:00+02:00",
        endTime = "2026-04-25T07:00:00+02:00",
        lineup = listOf(Artist("CAIVA"), Artist("Frederic."), Artist("Luxi Villar"), Artist("Part Time Killer"), Artist("Zorza")),
        description = "",
        url = "https://de.ra.co/events/2335994",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMDg2ZmFlMjJhZDRlMDdiMWU4ZDFlNjdiYWJkOGUyZDcyOGFkODg5Yi5wbmc=",
        price = 12.0
    ),

    // ── 25. April 2026 ──────────────────────────────────────────────────────

    Event(
        name = "Else Opening x Mutual Rytm",
        venueName = "Else",
        venueAddress = """Alt-Treptow 11
12435 Berlin""",
        latitude = 52.4837,
        longitude = 13.4482,
        startTime = "2026-04-25T14:00:00+02:00",
        endTime = "2026-04-26T08:00:00+02:00",
        lineup = listOf(
            Artist("Ana Rs"), Artist("Disguised"), Artist("Exos"), Artist("Human Safari"),
            Artist("Lewis Fautzi"), Artist("NEUX"), Artist("Regent"), Artist("SHDW")
        ),
        description = """Where is Else?
In the heart of East Berlin, right beside Treptower Park SBahn Station.
What if it rains?
Else's entire open-air dancefloor is covered by a transparent roof that will keep you warm and dry.
What's the sound system?
There's a brand new, high-end sound system: Lambda Labs QX3 and a new DJ booth, which is surrounded by the crowd from all sides.
When does the indoor room open?
After 22:00 'til the early hours.
Is there food?
Oven-baked pizza all-day.
Is there a chill-out space?
Hang out and have a drink at our riverside terrace.
What's the minimum age for entry?
21+
Do you take cash or card?
We only accept CARD payment throughout the club.
Where can I buy a ticket?
Tickets are available on RA or at the door with a CARD payment.
RA ticket does not guarantee entry; purchased tickets will be refunded in that case.""",
        url = "https://de.ra.co/events/2348436",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vYWZjMDlmNDE3ZDNhMDJiNjBlMGEyMDQwNGFjZWU3ZGVhOGZiNTQ2YS5qcGc=",
        price = 15.0
    ),
    Event(
        name = "Sound of Hell with Niki Istrefi, Indecorum, cassandrah, Akira & many more",
        venueName = "OST",
        venueAddress = """Revaler Straße 99
10245 Berlin""",
        latitude = 52.5012,
        longitude = 13.4636,
        startTime = "2026-04-25T23:00:00+02:00",
        endTime = "2026-04-26T10:00:00+02:00",
        lineup = listOf(
            Artist("Akira (Hong Kong Violence)"), Artist("Bildgewalt"), Artist("Berzaerk"), Artist("BOCHKA5000"),
            Artist("cassandrah"), Artist("Electra (2)"), Artist("Hoebie"), Artist("Indecorum"), Artist("Khyodo"),
            Artist("Mutilater"), Artist("Niki Istrefi"), Artist("oriole (PL)"), Artist("SAS (2)"), Artist("TO-WA")
        ),
        description = "Are you ready for the second hard edition? Industrial, hardcore, gabber, hard techno. Anything your hardcore heart desires.",
        url = "https://de.ra.co/events/2375943",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMTFiMjQzOTVlNGViMjhiMjMwNDJiMjc3ZmM2MGQ3NDFmZjJmYzg5YS5wbmc=",
        price = 13.0
    ),
    Event(
        name = "WHALIEN pres. AREA ONE B2B Niotech (ALL NIGHT LONG) invites Doruksen, 3LEEZA + friends",
        venueName = "Lokschuppen Berlin",
        venueAddress = """Revaler Straße 99
10245 Berlin""",
        latitude = 52.5063,
        longitude = 13.4537,
        startTime = "2026-04-25T23:00:00+02:00",
        endTime = "2026-04-26T09:00:00+02:00",
        lineup = listOf(
            Artist("AREA ONE B2B Niotech (All Night Long)"), Artist("Doruksen"), Artist("3LEEZA"),
            Artist("HANA"), Artist("THISO"), Artist("Westfall"), Artist("Jannik van der Vegt"),
            Artist("EZA (DE)"), Artist("SSXXCH"), Artist("DeBenjiy"), Artist("RE5")
        ),
        description = "",
        url = "https://de.ra.co/events/2299756",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMWY1ZTc2NTY0YTc0MjhkYjliMzE4YzIzMDY5ZTA0YzQ0Mjc5NTQxNC5wbmc=",
        price = 12.0
    ),
    Event(
        name = "REINKARNATION w/ KLING&KLANG, TERRA TWIINS, Trancestrudel, LISTORIO b2b Blame the Booker",
        venueName = "AEDEN",
        venueAddress = """Revaler Straße 99
10245 Berlin""",
        latitude = 52.5003,
        longitude = 13.4232,
        startTime = "2026-04-25T23:00:00+02:00",
        endTime = "2026-04-26T09:00:00+02:00",
        lineup = listOf(
            Artist("Blame the Booker b2b LISTORIO"), Artist("TERRA TWIINS"), Artist("KLING&KLANG"),
            Artist("Trancestrudel"), Artist("Filialleiter"), Artist("bouncy cat"), Artist("Elias Nuit (2)"), Artist("Gerry Lady")
        ),
        description = """The next REINKARNATION unfolds.
NEUTRUM returns to Aeden Club on April 25th for the next chapter of our journey.
What started as a powerful debut now evolves into a deeper, louder continuation.
Expect a high-intensity journey through Bounce, Trance, Hardtrance and Hardtechno - fast rhythms, euphoric peaks, and unstoppable momentum from start to finish.""",
        url = "https://de.ra.co/events/2339848",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vOWEyMzZlMGQwZDUzMzgzYTA0YzFmZDgxYTM3MTExZTZjMzhkNGFjOC5wbmc=",
        price = 12.0
    ),
    Event(
        name = "Dominik Eulberg, Andrea Botez, Bebetta, biskuwi, Sandar Sanchez and many more",
        venueName = "Ritter Butzke",
        venueAddress = """Ritterstraße 26
10969 Berlin""",
        latitude = 52.4989,
        longitude = 13.4014,
        startTime = "2026-04-25T22:00:00+02:00",
        endTime = "2026-04-26T07:00:00+02:00",
        lineup = listOf(
            Artist("Dominik Eulberg"), Artist("Andrea Botez"), Artist("biskuwi"), Artist("WENZL"),
            Artist("Bebetta"), Artist("Sandar Sanchez"), Artist("Dario Milkovic"), Artist("Claudius (DE)")
        ),
        description = "Tickets: https://club.ritterbutzke.com/event/250426-AndreaBotez-biskuwi",
        url = "https://de.ra.co/events/2362058",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vOWM3N2FiNzQzOTgzODFkZjgyNGM3NDEyODAzYTY5Njc0M2Y2NDM5Mi5qcGc=",
        price = 15.0
    ),
    Event(
        name = "NUDE with XDB, Victor, Naone, Random B, Elena Bi",
        venueName = "Prince Charles",
        venueAddress = """Prinzenstraße 85f
10969 Berlin""",
        latitude = 52.4988,
        longitude = 13.4094,
        startTime = "2026-04-25T22:00:00+02:00",
        endTime = "2026-04-26T12:00:00+02:00",
        lineup = listOf(Artist("XDB"), Artist("Victor (DE)"), Artist("Naone"), Artist("Elena Bi"), Artist("Random B")),
        description = """NUDE BERLIN | APRIL 25 | PRINCE CHARLES
LineUp A-Z
Elena Bi
Naone
Random B
Victor
XDB
Doors: 10:00PM - NOON
LETS DANCE!
Kisses,
NUDE
____
NUDE was created as a safe space for our community and has a zero tolerance for any form of discrimination or disrespectful behavior.
If you witness inappropriate behavior or feel unwell, please reach out to our awareness team located at the designated spot.
No photos on the dance floor!
Presale does not guarantee admission!
Age Disclaimer 21+
Artwork by @marutonkun""",
        url = "https://de.ra.co/events/2402743",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMTU3NTJlYWY1NWVjM2MzNDcwNzFlZDkxYjc0OWEwNjljYjc3NzI1NC5qcGc=",
        price = 13.0
    ),
    Event(
        name = "Ploetzlich x Vybrant Vault w/ BIDOBEN, lil.lili (live), Christa K, Atta, Oblique",
        venueName = "MaHalla",
        venueAddress = """Herzbergstraße 40-43
10365 Berlin""",
        latitude = 52.4722,
        longitude = 13.5512,
        startTime = "2026-04-25T12:00:00+02:00",
        endTime = "2026-04-25T22:00:00+02:00",
        lineup = listOf(
            Artist("ATTA (GER)"), Artist("BIDOBEN"), Artist("Christa K"), Artist("lil.lili (Live)"),
            Artist("Jetsk.ian"), Artist("oblique (DE)"), Artist("Coop"), Artist("Jacques Caques")
        ),
        description = """We're so excited to invite you back for another chapter in our off-location series at MaHalla - a day dedicated to sound, connection and the people who shape our community.
On April 25th, the space once again becomes a place for movement, shared moments and a carefully selected musical journey. Beyond the dance floor this gathering is about being present and celebrating the energy that unites us.
Let's come together around good music and keep this culture alive!""",
        url = "https://de.ra.co/events/2362812",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNTc1NjZiYTljMDdkZTY0ZjE1YjA1NjY2YjFkNDY1MDMxMWRmNGMwZC5qcGc=",
        price = 10.0
    ),
    Event(
        name = "XTRUDE with Grace Dahl, Identified Patient, LDS, Alpha Tracks and Angel D'lite",
        venueName = "RSO.BERLIN",
        venueAddress = """Richard-Sorge-Straße 25a
10249 Berlin""",
        latitude = 52.4662,
        longitude = 13.5038,
        startTime = "2026-04-25T23:59:00+02:00",
        endTime = "2026-04-27T08:00:00+02:00",
        lineup = listOf(
            Artist("Alpha Tracks [ROBUS]"), Artist("Andy Garvey [ROBUS]"), Artist("Glaskin [ROBUS]"),
            Artist("Grace Dahl [ROBUS]"), Artist("Identified Patient [ROBUS]"), Artist("I-RO [ROBUS]"),
            Artist("LDS live [ROBUS]"), Artist("MATRIX3K vinyl set [ROBUS]"),
            Artist("Angel D'lite [SUMME]"), Artist("Eoin DJ [SUMME]"), Artist("Fais Le Beau [SUMME]")
        ),
        description = """Angel D'lite is a London-based DJ and producer known for euphoric, high-energy sets blending '90s rave, hardcore, jungle and progressive trance. Emerging from London's underground party network, she brings an ecstatic and empathetic energy to the dancefloor, crafting moments that feel both playful and immersive. Her fractal approach to sound - heard on releases like Dolphins Have Sex for Pleasure and EPs on Ritual Poison and Planet Euphorique - cements her as a key figure in the contemporary queer rave landscape. On April 25th, she joins the SUMME floor alongside Fais Le Beau and Eoin DJ, while the ROBUS floor hosts Grace Dahl, LDS (live), Alpha Tracks and more to close out the first month of our fifth season, XTRUDE.
ADDITIONAL INFO:
Cash and cashless payment
DIRECTIONS:
Via S-Bahn - 4 stops from Ostkreuz or 3 stops from Neukoelln and then a 5 minute walk from Schoeneweide station.
Via Bus 165 - 18 minute ride from Schlesisches Tor to Obrikatstr. and then a 3 minute walk from the stop.
ONLINE PRESALE:
Please note that having a presale ticket does not assure admission. The club reserves the right to decline entry, with tickets being refunded accordingly. Please anticipate potential delays due to enhanced security checks at the entrance, even for ticket holders. Your cooperation and understanding are greatly appreciated. Thank you.""",
        url = "https://de.ra.co/events/2390212",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNzQ4MGEwNzAzYmQxYWMwMWZlNWRkZjU2ZDJkM2RkNGYyMmNhMGI3Zi5qcGc=",
        price = 15.0
    ),
    Event(
        name = "BRAINFIRE -THE ANNIVERSARY-",
        venueName = "Void Club",
        venueAddress = """Wiesenweg 5-9
10365 Berlin""",
        latitude = 52.5040,
        longitude = 13.4650,
        startTime = "2026-04-25T22:30:00+02:00",
        endTime = "2026-04-26T08:30:00+02:00",
        lineup = listOf(
            Artist("THE DJ PRODUCER (UK)"), Artist("ROB GEE (US)"), Artist("APAC (NL)"), Artist("BEAGLE (DE)"),
            Artist("BEAVYZ (DE)"), Artist("BEATRONAUTS (DE)"), Artist("BRAINCRACKER (DE)"), Artist("CAPCOM (CH)"),
            Artist("CHEECH (DE)"), Artist("DJ303 (DE)"), Artist("DJ TENSE (DE/US)"), Artist("FANATIC NOIZE KILLER (DE)"),
            Artist("FLIPCORE (DE)"), Artist("GABBA FRONT BERLIN (DE)"), Artist("KREISLAUFSTÖRUNG (DE)"),
            Artist("OUTRAGE (DE)"), Artist("PARDONAX (NL)"), Artist("ROBSEN (DE)"), Artist("SCHMERZBRINGER (DE)"),
            Artist("SPEEDCOREGEWALT (DE)"), Artist("SPLATTER (PL)"), Artist("TASSADAR (DE)"),
            Artist("THE ENTERTAINER (DE)"), Artist("TRASH FUCKER (DE)"), Artist("UPZET (DE)"),
            Artist("VIRUS 136 (DE)"), Artist("XTNTA (CO)"), Artist("ZUE HARD (DE)")
        ),
        description = """Three Decades of Unstoppable Hardcore.
One Night to Destroy Everything. Gabba Nation Records & Gabba Front Berlin celebrate 30 YEARS of raw Berlin Gabba - born in the smoke-filled cellars of the Bunker, forged in sweat, adrenaline, and kicks that break time itself. Together with Brainfire Events, we explode 25 YEARS of brain-melting madness and unbreakable nights:
BRAINFIRE - The Anniversary Edition!
Saturday, April 25, 2026 - VOID Berlin (Club + Hall)
The temple of pure devastation awaits. One night. Three floors. No mercy. Gabba, Hardcore, Speedcore - from 90s roots to modern annihilation. The Legendary Headliners take center stage and lead the charge:
Rob Gee (US) - The Gabber Guru himself!
From his explosive 1993 prank-track "Ecstasy, You Got What I Need" (Gold status, #1 video, Thunder Awards winner) to fusing Gabber with metal, hip-hop, and hardcore (collabs with Slipknot, Hatebreed, Biohazard, System Of A Down), he brought #PositivianVibes to the world. His eternal anthem "Gabber Up Your Ass" is pure legend - live he delivers chaos, unstoppable energy, and motivation. The man who turned a diss into a global movement. He's back to unite the family!
The DJ Producer (UK) - The undisputed king of UK Hardcore since 1994!
A true DJ's DJ with superhuman layering skills - he molds, shreds, and rebuilds sounds live in frenetic, pinpoint-precise sets that redefine what DJing can be. Producing since the early 90s, deeply rooted in scratch and rave culture, he built the "UK Hardcore Techno" sound and remains one of the most respected forces in the raucous underground.
Backed by an international hardcore invasion of 28 acts from 7 countries.
30 YEARS BERLIN GABBA + 25 YEARS BRAINFIRE
We never left. We just got harder, louder, and more global.""",
        url = "https://de.ra.co/events/2359212",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vM2FmMzBjNjMzMjllZDEyMjJkMzliN2M0Mzc4YzlkZTEwMTVmZGIzOS5qcGc=",
        price = 20.0
    ),

    // ── 26. April 2026 ──────────────────────────────────────────────────────

    Event(
        name = "Else Opening x KiNK & Marie Montexier invite",
        venueName = "Else",
        venueAddress = """Alt-Treptow 11
12435 Berlin""",
        latitude = 52.4837,
        longitude = 13.4482,
        startTime = "2026-04-26T14:00:00+02:00",
        endTime = "2026-04-27T06:00:00+02:00",
        lineup = listOf(
            Artist("Cosmo (KR)"), Artist("KiNK b2b Marie Montexier"), Artist("Mor Elian"),
            Artist("Nesa Azadikhah"), Artist("Raredub b2b KiNK"), Artist("SAM (9)"),
            Artist("Shonky b2b Marie Montexier")
        ),
        description = """Where is Else?
In the heart of East Berlin, right beside Treptower Park SBahn Station.
What if it rains?
Else's entire open-air dancefloor is covered by a transparent roof that will keep you warm and dry.
What's the sound system?
There's a brand new, high-end sound system: Lambda Labs QX3 and a new DJ booth, which is surrounded by the crowd from all sides.
When does the indoor room open?
After 22:00 'til the early hours.
Is there food?
Oven-baked pizza all-day.
Is there a chill-out space?
Hang out and have a drink at our riverside terrace.
What's the minimum age for entry?
21+
Do you take cash or card?
We only accept CARD payment throughout the club.
Where can I buy a ticket?
Tickets are available on RA or at the door with a CARD payment.
RA ticket does not guarantee entry; purchased tickets will be refunded in that case.""",
        url = "https://de.ra.co/events/2348437",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMGY5ZjZlZGNiODRiZTI5NGMzZTdlYzIwM2U5Y2Y5YTM5ZGVkMDg0YS5qcGc=",
        price = 15.0
    ),
    Event(
        name = "Kedi Bounce X Rafiki",
        venueName = "Fitzroy",
        venueAddress = """Fichtestraße 4a
10967 Berlin""",
        latitude = 52.5138,
        longitude = 13.4096,
        startTime = "2026-04-26T16:00:00+02:00",
        endTime = "2026-04-27T00:00:00+02:00",
        lineup = listOf(
            Artist("Anele [Rafiki]"), Artist("Katerinha [Rafiki]"), Artist("Khadija (DE) [Rafiki]"),
            Artist("Njeri [Rafiki]"), Artist("critical P [Kedi Bounce]"),
            Artist("Jean-Jez [Kedi Bounce]"), Artist("Kang (Mesmer) [Kedi Bounce]")
        ),
        description = """Keid Bounce x Rafiki's BDAY BASH
Sunday April 26th
4 pm - midnight
TICKETS ONLY AT THE DOOR
Our anticipation for this event can not be understated. Kedi Bounce are collaborating with Rafiki to bring an afternoon of eclectic and vibrant music to your ears. You can expect a mix of styles and sounds, centered around various Afro-diasporic genres, delivered by top-notch local selectors.
The Rafiki crew will be in full attendance with Aneleon, Katerinha, Khadija, and Njeri laying it down. Kedi Bounce will see the dynamic trio - critical P, Jean-Jez and Mesmer up to their usual shenanigans.
Our home for this afternoon will be at Fitzroy, a small intimate venue perfect for the social & vibrant atmosphere this event aims to provide. Come join us, create new connections and maintain existing ones as Rafiki & Kedi Bounce turn 3 years old.
The date will be the 26th April, a Sunday afternoon starting at 16:00. The perfect way to end the weekend. We're looking forward to your company.
Hiss less, purr more marafiki.""",
        url = "https://de.ra.co/events/2405247",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vYjI5N2RjN2YwNTE3YmQ2MGE5ZTdlYTBlOWUwNzEyMTIzYTU1OGViNS5qcGc=",
        price = 8.0
    ),
    Event(
        name = "CUNTCORE w/ ketia, Dj handbag, DJ Egoshooter",
        venueName = "Paloma",
        venueAddress = """Skalitzer Straße 5
10999 Berlin""",
        latitude = 52.4993,
        longitude = 13.4186,
        startTime = "2026-04-26T14:00:00+02:00",
        endTime = "2026-04-26T23:59:00+02:00",
        lineup = listOf(
            Artist("Adri Alibi"), Artist("Dj handbag"), Artist("ketia"),
            Artist("Nicky Miller x Electrosexual (live)"), Artist("DJ.Egoshooter10000")
        ),
        description = """Sunday Service: CUNTCORE
Sat 26. April | Paloma, Berlin | 14:00 - 00:00
CUNTCORE takes over Paloma for a full day-to-midnight ritual - a slow build into full-body release, held together by groove, grit and that specific kind of queer tenderness that only shows up when the room commits.
Think sweat without rush, hooks without apology and pressure that keeps rising until midnight. Come early for the first pull, stay late for the final stretch: this one's designed as a continuous arc, not a highlights reel.
Adri Alibi, DJ EGOSHOOTER, DJ Handbag, ketia, Nicky Miller x Electrosexual (live)""",
        url = "https://de.ra.co/events/2399268",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMTVmZGQ1MmQxY2IxYmRlYmJiMjhlNzYzZWVlYzI0NjRkNGVhMTEyNy5wbmc=",
        price = 10.0
    ),
    Event(
        name = "Silly Little Rave",
        venueName = "Panke",
        venueAddress = """Gerichtstraße 23
13347 Berlin""",
        latitude = 52.5454,
        longitude = 13.3740,
        startTime = "2026-04-26T16:00:00+02:00",
        endTime = "2026-04-27T00:00:00+02:00",
        lineup = listOf(Artist("Robot Girlfriend"), Artist("80HDJ"), Artist("Vitling"), Artist("DJ STRAIGHT GIRL")),
        description = """Spring has sprung, and that means your favorite silly geese are migrating north. The next Silly Little Rave is coming to roost at Panke Culture on April 26th from 16:00 - 00:00!
Joining the flock for this edition is someone who is no stranger to our new nesting grounds: dj straight girl! They will be honking, tweeting, wubbing, and warbling with a specially curated selection for our spring celebration.
Our residents have also been incubating an egg-ceptional nest-full of new bangers for you all. Our flightless flaming-hoes 80HDJ, Robot Girlfriend, and Vitling will serve up a series of birdsongs and pleasure calls that will get you moonwalking like a manakin. Make sure to bring your best dance moves, maybe you'll attract a mate.
A limited number of early bird tickets available for pre-sale!
Or at the door:
16:00-17:00 entry for 10 EUR
After 17:00 entry for 15 EUR""",
        url = "https://de.ra.co/events/2402651",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vN2NhNzE3MTRjM2U1NzZjNjI2NGQxZjYzM2QyYjc5NDE4ZjViNGY3Zi5qcGc=",
        price = 15.0
    ),
    Event(
        name = "wieder: BOILER ROOM SETUP + MARKETPLACE",
        venueName = "Lokschuppen Berlin",
        venueAddress = """Revaler Straße 99
10245 Berlin""",
        latitude = 52.5063,
        longitude = 13.4537,
        startTime = "2026-04-26T21:00:00+02:00",
        endTime = "2026-04-27T04:00:00+02:00",
        lineup = listOf(Artist("EGE363"), Artist("subcutan"), Artist("LYBRA"), Artist("elvito")),
        description = """THIS SUNDAY!
Looking forward to see these people behind the decks, and looking forward to see you guys dancing to their tracks!
Those who don't know: we have free tickets available here (or via the link in our Instagram bio). Grab those if you'd like to enter for free until 22:30.
If you want to enter at any time during the party there are also 5,50 eur tickets available until this Sunday! Tickets at the door will cost 10 eur!
See yall soon""",
        url = "https://de.ra.co/events/2402606",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMDFkMWM1NTUyMTM0ZjNiNjA0Mjk3OGVjNzhhZWE3YzA4MTVhMGI5NC5wbmc=",
        price = 10.0
    ),
    Event(
        name = "Out of Office: Warm Up",
        venueName = "Mom's Limousine Service",
        venueAddress = """Revaler Straße 99
10245 Berlin""",
        latitude = 52.5221,
        longitude = 13.3843,
        startTime = "2026-04-26T15:00:00+02:00",
        endTime = "2026-04-27T01:00:00+02:00",
        lineup = listOf(
            Artist("Vio PRG"), Artist("The Office"), Artist("KIRSCH"),
            Artist("Fletchy Boy (live)"), Artist("DJ Pattex b2b Mystigrix")
        ),
        description = """Dear Recipient,
We're pleased to invite you to the official Warm-Up sync for our upcoming 3-day festival, Out Of Office 2026 - taking place on 26 April at Mom's Limousine Service.
Please note: Entry is free of charge for all festival ticket holders. For everyone else, a limited number of Warm-Up-only spots will be available.
Ahead of clocking out, we're gathering our finest desk jockeys and you for a first shift on the dancefloor. As an extra special, our beloved colleagues from Suppa will be serving culinary creations and offering a small preview of the festival kitchen.
Let's log off, together.
Kind regards,
Your OOO Team""",
        url = "https://de.ra.co/events/2396507",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNDFkZjBiYTc1MzliZjk0MDBkYjVjMTRkMGM3YjIxMzBjMmRiN2M1MC5qcGc=",
        price = 0.0
    ),
    Event(
        name = "Beate Barfuss /// Kati Color b2b Sidamo Soundsystem, Ninze, Ektoplast",
        venueName = "Beate Uwe",
        venueAddress = """Cuvrystraße 7
10997 Berlin""",
        latitude = 52.5155,
        longitude = 13.4212,
        startTime = "2026-04-26T19:00:00+02:00",
        endTime = "2026-04-27T04:00:00+02:00",
        lineup = listOf(
            Artist("Kati Color b2b Sidamo Soundsystem"), Artist("Ninze"), Artist("Ektoplast")
        ),
        description = """Intimacy needs space and time.
When we roll out our carpets on Sundays, we create both - to bring shared experiences back to our center and exchange them with tenderness.
A conscious slowdown, a soulful sway - accompanied by a curated selection of passionate artists from the downtempo scene.
cozy & captivating;
laid-back & driving;
mindful & connected.
- just be you. -
---
Kati Color: Born by the Baltic Sea and raised in Chemnitz, Kati Color's roots shape her versatile sound. She blends Minimal, Microhouse and Deep Tech with the tribal, spiritual soundscapes of Downtempo and Organic House, enriched by influences from Hip Hop, Dub and Drum'n'Bass. Guided by her Mayan seal Cauac, she takes listeners on transformative journeys to feel, explore, dream and dance. Her mixes combine trippy, deep and driving grooves with soul and style, bringing grounded mindfulness to the floor and creating meaningful moments of flow and connection.
Sidamo Soundsystem: Sidamo Soundsystem creates organic, downtempo journeys that fuse deep tech, house, and world influences into a soulful whole. Their sets, often themed around times of day or natural cycles, move between earthy grooves and cosmic atmospheres, weaving rhythms that feel both grounding and transcendent.
Ninze: Over the past five years, Ninze established a significant and experimental sound, also called Ketapop by creating a spherical, yet melancholic atmosphere with a distinctive deepness and complex sound patterns. Noises from out of space and moving baselines stirring up the creatures who got lost in his slow but forward-sound, taking the audience to places where they haven't been before and leaving them in gentle confusion.
Ektoplast: Ektoplast is a composer, sound artist, and DJ moving between downbeat, house, ambient, and acousmatic music. His work blends club-oriented depth with experimental sonic landscapes, unfolding both on the dancefloor and within immersive sound and multimedia installations.
---
Tickets at the door. Card payments only at the bar.
Beate Uwe is accessible for people with disabilities and is a non-smoking club.
All are welcome at Beate Uwe and every person should feel safe and comfortable! Zero tolerance for racism, sexism, antisemitism, queer-phobia, ableism, or any other form of discrimination.""",
        url = "https://de.ra.co/events/2404383",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMDVkYjVhNWVlMzVjMjFlNzYyYmZkYzMxMDhhNzg0ZjA5OTZiNjVkZi5wbmc=",
        price = 8.0
    ),
    Event(
        name = "Zanias feat. Korine in Berlin",
        venueName = "Frannz Club",
        venueAddress = """Schönhauser Allee 36
10435 Berlin""",
        latitude = 52.5393,
        longitude = 13.4136,
        startTime = "2026-04-26T19:00:00+02:00",
        endTime = "2026-04-26T23:30:00+02:00",
        lineup = listOf(Artist("Zanias"), Artist("Korine")),
        description = """Out Of Line Music presents
ZANIAS
Plus special guests: KORINE
26 April 2026
FRANNZ Club
Schoenhauser Allee 36, 10435 Berlin
Tickets: 25 EUR
Doors: 19:00 | Begin: 20:00
Out Of Line presents an immersive night of dark electronic pop, coldwave and post-industrial energy as Zanias returns to Berlin for a special headline performance, joined by Philadelphia synth-pop duo Korine.
Zanias, the project of Australian-born, Berlin-based artist Alison Lewis, is a defining voice in modern darkwave and EBM. From her early work with Linea Aspera and Keluar to co-founding Berlin's influential Fleisch collective, she has helped shape today's underground electronic scene. Her recent albums Chrysalis, Ecdysis, and 2025's Cataclysm mark a powerful evolution of sound, blending ethereal vocals, driving body-music rhythms, and emotionally charged songwriting. Cataclysm delivers her most high-energy and dance-driven material to date, confronting themes of transformation, crisis and resilience.
Special guests Korine bring their signature mix of dark synth-pop, post-punk, and new-wave nostalgia. Known for pairing upbeat, hook-driven electronics with melancholic and introspective lyrics, the duo have built a strong international following through acclaimed releases and standout festival appearances, including Wave-Gotik-Treffen.
Expect a night of sweeping synths, pulsing basslines and cathartic melodies; essential for fans of modern dark electronic and pop in Berlin.""",
        url = "https://de.ra.co/events/2358741",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMzhjNzViNzBhMTgwNzQxM2Q5NTRiN2EwNDM3ZTk1Yjc0MDg1ODA2Yi5qcGc=",
        price = 25.0
    ),
    Event(
        name = "BLVSH 6th Anniversary",
        venueName = "TBA - crazylegz",
        venueAddress = """Pannierstraße 6
12047 Berlin""",
        latitude = 52.4890,
        longitude = 13.4320,
        startTime = "2026-04-26T14:00:00+02:00",
        endTime = "2026-04-26T20:00:00+02:00",
        lineup = listOf(
            Artist("INVERNO"), Artist("Acidfinky"), Artist("Ka (DE)"), Artist("hripsime"), Artist("femnms"), Artist("Zahra"), Artist("KYT")
        ),
        description = """For its 6th anniversary, your favourite FLINTA collective returns for a daytime event, full of surprises and focusing on what united the BLVSH members at the first place: vinyl records.
We will be celebrating our anniversary at crazylegz in the heart of NK, with a full FLINTA and vinyl-only lineup. With residents and guests.
Stay tuned!""",
        url = "https://de.ra.co/events/2399583",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vZmUxYjU4N2FjMDVmYmJjMGNjYjNmN2EyOTY4ODUxNWYwZGZkYjdjYi5wbmc=",
        price = 8.0
    ),
    Event(
        name = "JKs Ort zum Sonntag",
        venueName = "Jonny Knueppel",
        venueAddress = """Knaackstraße 22-24
10405 Berlin""",
        latitude = 52.5362,
        longitude = 13.4284,
        startTime = "2026-04-26T10:00:00+02:00",
        endTime = "2026-04-26T21:00:00+02:00",
        lineup = listOf(
            Artist("karete bu"), Artist("Flandez"), Artist("Alessia Ceruti"), Artist("Frivolous"), Artist("Pia Kauitl")
        ),
        description = """JKs After Hour fuer die gute Landung
Spiel und Spass unter
Robinien und Birken
Saisondebut""",
        url = "https://de.ra.co/events/2417144",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vMmRiOTFmOWY5MzY2YTkyNDMwNTc5YWU3NjVkNjAxYzk3OGNmZjYzOS5qcGc=",
        price = 5.0
    ),

    // ── 30. April 2026 ──────────────────────────────────────────────────────

    Event(
        name = "Savory - Tanz in den Mai",
        venueName = "OST",
        venueAddress = """Revaler Straße 99
10245 Berlin""",
        latitude = 52.5012,
        longitude = 13.4636,
        startTime = "2026-04-30T23:00:00+02:00",
        endTime = "2026-05-01T06:00:00+02:00",
        lineup = listOf(
            Artist("Charleen Herzig"), Artist("DETOXX"), Artist("Phuture Traxx"), Artist("two girls one mom")
        ),
        description = """Lets celebrate and welcome May together.
Club OST is cashless.
No Photo/ Video Policy""",
        url = "https://ra.co/events/2394295",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNmM3MTBmMGVmMWYyMjIyMjkzZWUxNzljZjE1MTdkOTJhNzQzZDA3NS5wbmc=",
        price = 12.0
    ),
    Event(
        name = "FREE PARTY - TANZ IN DEN MAI",
        venueName = "Void Club",
        venueAddress = """Wiesenweg 5-9
10365 Berlin""",
        latitude = 52.5040,
        longitude = 13.4650,
        startTime = "2026-04-30T23:00:00+02:00",
        endTime = "2026-05-01T08:00:00+02:00",
        lineup = listOf(
            Artist("Cat Vermillion"), Artist("Orpheuz"), Artist("Der Eggert (bday)"), Artist("Ezekiel (DE)"),
            Artist("Modulatos"), Artist("Teddy Westside"), Artist("Upzet"), Artist("Kenzura"), Artist("Itzaletan Sua"),
            Artist("Mr.Lafont"), Artist("IHOPEIEXIST"), Artist("MAED"), Artist("ROSS z"), Artist("Proma"), Artist("TommyB (2)")
        ),
        description = """Free Party | VOID Club & Hall
Thu, 30.04.2026 | Drum & Bass, Techno, Groove on 3 Areas
Der Eggert & Modulatos Bday Techno Floor / VOID Dnb Floor / NoHype Groove & Hardgroove Floor
+ Chillout Area
+ Outdoor Area
Please get a free ticket, to spread the love
18+ Event
Doors: 23:00h
Entry: 0 EUR
Donations are welcome
No Dresscode!
No Photos!
No Racism, No Sexism, No Homophobia, No Transphobia
Of course, the free ticket does not guarantee entry!
VOID Club & Hall - Wiesenweg 5-9 | 10365 Berlin
S-Ostkreuz | S+U Frankfurter Allee | S-Noeldner Platz""",
        url = "https://ra.co/events/2388829",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNmRhZTI4ZjlkMmFjOWUxNzg5ZTIzOTJlYzNmMjVjZWQ5NjQ4NDFlMi5wbmc=",
        price = 0.0
    ),

    // ── 1. Mai 2026 ─────────────────────────────────────────────────────────

    Event(
        name = "Else: MAYDAY (free open air)",
        venueName = "Else",
        venueAddress = """Alt-Treptow 11
12435 Berlin""",
        latitude = 52.4837,
        longitude = 13.4482,
        startTime = "2026-05-01T14:00:00+02:00",
        endTime = "2026-05-02T08:00:00+02:00",
        lineup = listOf(
            Artist("Bae Blade"), Artist("DJ Frank x Rosa Red"), Artist("lizaliza"),
            Artist("LOVEFOXY"), Artist("Spacer Woman"), Artist("Talia Dorr"), Artist("Tjade")
        ),
        description = """MAYDAY at Else. Free Entry until 9pm.
Where is Else?
In the heart of East Berlin, right beside Treptower Park SBahn Station.
What if it rains?
Else's entire open-air dancefloor is covered by a transparent roof that will keep you warm and dry.
What's the sound system?
There's a brand new, high-end sound system: Lambda Labs QX3 and a new DJ booth, which is surrounded by the crowd from all sides.
When does the indoor room open?
After 22:00 'til the early hours.
Is there food?
Oven-baked pizza all-day.
Is there a chill-out space?
Hang out and have a drink at our riverside terrace.
What's the minimum age for entry?
21+
Do you take cash or card?
We only accept CARD payment throughout the club.
Where can I buy a ticket?
Tickets are available on RA or at the door with a CARD payment.
RA ticket does not guarantee entry; purchased tickets will be refunded in that case.""",
        url = "https://ra.co/events/2385664",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vYTQyNzY4Yzc2MDE5MjJlNzg2ZmRlNzNmM2MzNmYxZTk3ZTM4NDhmYy5qcGc=",
        price = 0.0
    ),
    Event(
        name = "OST Tanz in den Mai - Mega Free Open Air",
        venueName = "OST",
        venueAddress = """Revaler Straße 99
10245 Berlin""",
        latitude = 52.5012,
        longitude = 13.4636,
        startTime = "2026-05-01T12:00:00+02:00",
        endTime = "2026-05-02T12:00:00+02:00",
        lineup = listOf(
            Artist("7522"), Artist("abname"), Artist("Aidan"), Artist("Akua"), Artist("Alex Kassian"), Artist("Alexander Arpeggio"),
            Artist("Alicia Carrera"), Artist("Anacalypto"), Artist("Audrey Danza"), Artist("Barry Sunset"),
            Artist("Bell Towers"), Artist("Benedikt Frey"), Artist("Blu:sh"), Artist("Blue Hour"),
            Artist("ilbroccolovolante"), Artist("C3D-E"), Artist("Camila Ramirez"), Artist("Camilo Miranda"),
            Artist("Couple Looking For Bananas"), Artist("Courtesy"), Artist("Courtney Bailey"), Artist("Cromby"),
            Artist("Curses"), Artist("Dar Molloy"), Artist("David Diamond"), Artist("David Fogarty"), Artist("Denzel"),
            Artist("Desiree Falessi"), Artist("Diamin"), Artist("DINA"), Artist("DJ Aficionado"),
            Artist("DJ Fart in the Club"), Artist("DJ Lizzy Vittone"), Artist("DJ Teeth"), Artist("Dominik Andre"),
            Artist("E-Talking"), Artist("Egregore"), Artist("Eleonora K"), Artist("Eline"), Artist("Fantastic Man"),
            Artist("FFAN"), Artist("Field Notes DJs"), Artist("Frinda di Lanco"), Artist("Garcon Kiwi"),
            Artist("Geneva_"), Artist("Giulia Gutterer"), Artist("Hendrik Stein"), Artist("Hitomi"), Artist("Holten"),
            Artist("Hunee"), Artist("Jack Danzey"), Artist("Jackmaster Thomas"), Artist("Jaime Fiorito"),
            Artist("Jerky T"), Artist("Joao Comazzi"), Artist("Juan Ramos"), Artist("Jugin"), Artist("Karine"),
            Artist("Katia Curie"), Artist("Kidcat"), Artist("Kinzua"), Artist("Klara & Franka"), Artist("Llupe"),
            Artist("Lolsnake"), Artist("Luca Averna"), Artist("Luca Elsi"), Artist("Lucky Lube"),
            Artist("Martin Gilleshoej"), Artist("Matrixxman"), Artist("MFX"), Artist("Moretz"), Artist("Mr Sian"),
            Artist("MVHY"), Artist("Natalie Robinson"), Artist("Nive"), Artist("Nizar Sarakbi"), Artist("Okouru"),
            Artist("Pato"), Artist("PLO Man"), Artist("Richii"), Artist("Roberta Deflorio"), Artist("Rotciv"),
            Artist("Running Hot"), Artist("Samara"), Artist("Shakolin"), Artist("Snow (DE)"),
            Artist("Sound Metaphors Djs"), Artist("Soundstream"), Artist("Tania Just"), Artist("Telephones"),
            Artist("Temple Rat"), Artist("Tiago Oudman"), Artist("Tomok")
        ),
        description = """Ohne Eintritt zu verlangen, schmeisst das OST eine Mega-Mai-Party.
Club OST is cashless. No Photo/Video Policy.""",
        url = "https://ra.co/clubs/141987",
        flyerURL = "",
        price = 0.0
    ),
    Event(
        name = "RADIANCE 1st of May",
        venueName = "Atemporal",
        venueAddress = """Revaler Straße 99
10245 Berlin""",
        latitude = 52.5093,
        longitude = 13.4660,
        startTime = "2026-05-01T12:00:00+02:00",
        endTime = "2026-05-01T21:00:00+02:00",
        lineup = listOf(
            Artist("Dragana & Viktor Sloth"), Artist("Jeena"), Artist("Justin Shaffer"),
            Artist("monosym"), Artist("Delta Rain & Keeptress")
        ),
        description = """soft resistance
while the city accelerates
we reduce
a quiet refusal
to the straight line of noise
to the urgency of elsewhere""",
        url = "https://de.ra.co/events/2421256",
        flyerURL = "https://imgproxy.ra.co/_/quality:66/aHR0cHM6Ly9pbWFnZXMucmEuY28vNDRjMjQ2NmJiODJkMjBmNjRmOGQwN2NhMTNmMDNmOWMxYTI0MGU5Yy5qcGc=",
        price = 10.0
    )
)
