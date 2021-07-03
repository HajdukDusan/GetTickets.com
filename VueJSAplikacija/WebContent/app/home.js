Vue.component("home", {
  data: function () {
    return {
      manifestacije: [],
      manifestacijaNaziv: "",
      grad: "",
      cenaOd: "",
      cenaDo: "",
      datumOd: "",
      datumDo: "",
      sortBy: "Datum odrzavanja",
      tipManifestacije: "",
      statusKarte: "",
      smer: "Rastuce",
      raspolozivost: "",
      smerOptions: ["Rastuce", "Opadajuce"],
      sortByOptions: ["Lokacija", "Cena Karte", "Datum odrzavanja"],
      filterByTip: ["", "CONCERT", "FESTIVAL"],
      filterByRaspoloziv: ["", "Ima na lageru"],
      minCena: 0,
      maxCena: 0,
      cookie: "",
      role: null,
      prosecnaOcena: "",
    };
  },
  mounted() {
    this.role = localStorage.getItem("role");
    if (this.role == null) {
      this.role = "default";
    }
    this.cookie = localStorage.getItem("cookie");
    if (this.cookie == null) {
      this.cookie = "";
    }
    this.getManifestationsSearched();
  },
  template: `
    <div>
          <div v-if="role  === 'default'">
          <default-nav></default-nav>
        </div>
          <div v-if="role  === 'admin'">
          <admin-nav></admin-nav>
        </div>
        <div v-if="role  === 'worker'">
          <worker-nav></worker-nav>
        </div>
        <div v-if="role  === 'user'">
          <korisnik-nav></korisnik-nav>
        </div>
        <link rel="stylesheet" href="css/home.css" type="text/css">
	      <b-card id="page_content" style= "height: 100vh;
          overflow: hidden;
          overflow-y: scroll; 
          text-align: center;">
    
          <b-row>
            <b-col>
                <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Naziv</b-input-group-text>
                    </template>
                  <b-form-input
                      placeholder="Pretrazi po nazivu..."
                      v-on:input="getManifestationsSearched"
                      v-model="manifestacijaNaziv"
                      type="search"/>
                </b-input-group>
                </b-col>
               <b-col>
                  <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Grad</b-input-group-text>
                    </template>
                  <b-form-input
                      placeholder="Pretrazi po gradu..."
                      v-on:input="getManifestationsSearched"
                      v-model="grad"
                      type="search"/>
                       </b-input-group>
                </b-col>
                                <b-col>
                                 <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Cena od:</b-input-group-text>
                    </template>
                <b-form-input
                      placeholder="Maksimalnu cenu.."
                      v-model="cenaOd"
                      v-on:input="getManifestationsSearched"
                      type="number"
                      min = "0"/>
                      </b-input-group>
                </b-col>
                <b-col>
                 <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Cena do:</b-input-group-text>
                    </template>
                  <b-form-input
                      placeholder="Minimalnu cenu.."
                      v-model="cenaDo"
                      v-on:input="getManifestationsSearched"
                      type="number"/>
                       </b-input-group>
                </b-col>
                </b-row>
                <br>
                <b-row>
                <b-col>
                <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Datum od:</b-input-group-text>
                    </template>
                     <b-form-input
                                  v-model="datumOd"
                      v-on:input="getManifestationsSearched"
                      type="date"/>
                      </b-input-group>
                </b-col>
                                <b-col>
                <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Datum do:</b-input-group-text>
                    </template>
                    <b-form-input
                      v-model="datumDo"
                      v-on:input="getManifestationsSearched"
                      type="date"/>
                      </b-input-group>
                </b-col>
                
                </b-row>

              <br>
                <b-row>
                                <b-col>
                  <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Sort by</b-input-group-text>
                    </template>
                <b-form-select
                      placeholder="Izaberite sort"
                      v-model="sortBy"
                      :options="sortByOptions"
                      v-on:input="getManifestationsSearched"/>
                      </b-input-group>
                </b-col>
                <b-col>
                  <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Smer</b-input-group-text>
                    </template>
                <b-form-select
                      v-model="smer"
                      :options="smerOptions"
                      v-on:input="getManifestationsSearched"/>
                      </b-input-group>
                </b-col>
                                <b-col>
                  <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Tip manifestacije</b-input-group-text>
                    </template>
                <b-form-select
                      placeholder="Izaberite sort"
                      v-model="tipManifestacije"
                      :options="filterByTip"
                      v-on:input="getManifestationsSearched"/>
                      </b-input-group>
                </b-col>
                <b-col>
                  <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Raspolozivost</b-input-group-text>
                    </template>
                <b-form-select
                      placeholder="Izaberite sort"
                      v-model="raspolozivost"
                      :options="filterByRaspoloziv"
                      v-on:input="getManifestationsSearched"/>
                      </b-input-group>
                </b-col>
                
                
                
                </b-row>
              <b-row>

              </b-row>

                  <b-row >
                  <div class ="col-md-4" v-for="manifestacija in manifestacije">
                  <b-col>
                   <b-card
                      style="margin: 10px auto; max-width: 300px"
                      :header="manifestacija.name"
                      class="text-left"
                      bg-variant="light"
                      :img-src="getImgUrl(manifestacija)"
                  >
                    <b-card-text>
                      Tip: {{ manifestacija.manifestationType }} <br>
                      Datum: {{ fixDate(manifestacija) }} <br>
                      Cena karte: {{ manifestacija.regularPrice  }} <br>
                      Lokacija: {{ manifestacija.location.address  }}, {{ manifestacija.location.city  }}, {{ manifestacija.location.country  }}<br>
                      <div v-if="manifestacija.status === 'FINISHED'">
                        Prosecna ocena: {{ calculateAverage(manifestacija)  }} <br>
                      </div>
             		    </b-card-text>

                      <b-button v-on:click="loadManifestacija(manifestacija)" variant="primary">Pogledajte manifestaciju</b-button>
                     
                     </b-card>
                     
                    </b-col>
                    </div>
                  </b-row>
    </b-card>
    </div>
    `,

  methods: {
    calculateAverage(manifestacija) {
      axios
        .get(`rest/comment/averageScore/manifestation=${manifestacija.name}`)
        .then((response) => {
          manifestacija.prosecnaOcena = response.data;
          return response.data;
        });
      return manifestacija.prosecnaOcena;
    },
    izmeniManifestaciju(manifestacija) {
      localStorage.setItem("manifestacija", manifestacija.name);
      this.$router.push("/izmena-manifestacije");
    },
    loadManifestacija(manifestacija) {
      localStorage.setItem("manifestacija", manifestacija.name);
      this.$router.push("/prikaz-manifestacije");
    },
    fixDate(manifestacija) {
      manifestacija.dateTime = manifestacija.dateTime.split("T")[0];
      return manifestacija.dateTime;
    },
    getManifestations() {
      axios.get(`rest/manifestation/manifestations`).then((response) => {
        this.manifestacije = response.data;
        console.log(response.data);
      });
      console.log(this.manifestacije);
    },
    getManifestationsSearched() {
      axios
        .get(`rest/manifestation/searched`, {
          params: {
            manifestacija: this.manifestacijaNaziv,
            lokacija: this.grad,
            cenaOd: this.cenaOd,
            cenaDo: this.cenaDo,
            datumOd: this.datumOd,
            datumDo: this.datumDo,
            sortBy: this.sortBy,
            tipManifestacije: this.tipManifestacije,
            statusKarte: this.statusKarte,
            smer: this.smer,
            raspolozivost: this.raspolozivost,
          },
        })
        .then((resp) => {
          this.manifestacije = resp.data;
          console.log(this.manifestacije);
        });
    },
    getImgUrl(manifestacija) {
      return manifestacija.eventPoster;
    },
  },
});
