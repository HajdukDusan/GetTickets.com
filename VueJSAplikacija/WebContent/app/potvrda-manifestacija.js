Vue.component("potvrda-manifestacija", {
  data: function () {
    return {
      manifestacije: [],
      cookie: "",
      role: null,
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
    this.getManifestations();
  },
  template: `
    <div>
          <admin-nav></admin-nav>
          
        <link rel="stylesheet" href="css/home.css" type="text/css">
	      <b-card id="page_content" style= "height: 100vh;
  overflow: hidden;
  overflow-y: scroll; 
  text-align: center;">

                  <b-row >
                  <div class ="col-md-4" v-for="manifestacija in manifestacije">
                  <b-col v-if="manifestacija.status == 'PENDING'">
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
                      <div v-if="manifestacija.status == false">
                        Prosecna ocena: {{ manifestacija.regularPrice  }} <br>
                      </div>
             		    </b-card-text>

                      <b-button v-on:click="OdobriManifestacija(manifestacija)" variant="success">Odobri</b-button>
                      <b-button v-on:click="OdbiManifestacija(manifestacija)" variant="danger">Odbi</b-button>
                       
                     </b-card>
                     
                    </b-col>
                    </div>
                  </b-row>
    </b-card>
    </div>
    `,

  methods: {
    fixDate(manifestacija) {
      manifestacija.dateTime = manifestacija.dateTime.split("T")[0];
      return manifestacija.dateTime;
    },
    getManifestations() {
      axios.get(`rest/manifestation/pending-manifestations`).then((response) => {
        this.manifestacije = response.data;
        console.log(this.manifestacije);
      });
      console.log(this.manifestacije);
    },
    getImgUrl(manifestacija) {
      return manifestacija.eventPoster;
    },
    OdobriManifestacija(manifestacija) {
      axios.get(`rest/manifestation/approve/name=${manifestacija.name}`).then((response) => {
        console.log(response.data);
       	this.getManifestations();
      });
    },
    OdbiManifestacija(manifestacija) {
      axios.get(`rest/manifestation/deny/name=${manifestacija.name}`).then((response) => {
        console.log(response.data);
        this.getManifestations();
      });
    },
  },
});
