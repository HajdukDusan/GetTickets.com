Vue.component("pregled-korisnika", {
  data: function () {
    return {
      cookie: "",
      role: "",
      korisnici: [],
      ime: "",
      prezime: "",
      korisnicko: "",
      tipoviKorisnika: ["", "user", "worker", "admin"],
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.role = localStorage.getItem("role");
    this.loadKorisnici();
  },
  template: `
  
    <div>
    <admin-nav></admin-nav>
          <link rel="stylesheet" href="css/page_style.css" type="text/css">
	      <b-card id="page_content" style= "height: 100vh;
  overflow: hidden;
  overflow-y: scroll; 
  text-align: center;">
        <b-row>
                <b-col>
                <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Ime</b-input-group-text>
                    </template>
                    <b-form-input
                      placeholder="Pretrazi po imenu..."
                      v-model="ime"
                      type="search"/>
                </b-input-group>
                </b-col>
                <b-col>
                <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Prezime</b-input-group-text>
                    </template>
                    <b-form-input
                      placeholder="Pretrazi po prezimenu..."
                      v-model="prezime"
                      type="search"/>
                </b-input-group>
                </b-col>
                <b-col>
                <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Korisnicko ime</b-input-group-text>
                    </template>
                    <b-form-input
                      placeholder="Pretrazi po korisnicko imenu..."
                      v-model="korisnicko"
                      type="search"/>
                </b-input-group>
                </b-col>
                <b-col>
                <b-input-group>
                 <template #prepend>
                     <b-input-group-text >Tip korisnika</b-input-group-text>
                    </template>
                    <b-form-select
                    id="input-3"
                    v-model="korisnicko"
                    :options="tipoviKorisnika"
                    ></b-form-select>
                </b-input-group>
                </b-col>
        </b-row>
                      <b-row >
                  <div class ="col-md-4" v-for="korisnik in korisnici">
                  <b-col>
                   <b-card
                      style="margin: 10px auto; max-width: 300px"
                      class="text-left"
                      bg-variant="light"
                  >
                    <b-card-text>
                    {{korisnik.name}} {{korisnik.surname}} <br>
                    Korisnicko ime: {{korisnik.username}} <br>
                    Lozinka: {{korisnik.password}} <br>
                    Tip korisnika: {{korisnik.role}} <br>

                     </b-card>
                     
                    </b-col>
                    </div>
                  </b-row>
    
        </b-card>
    </div>
      `,

  methods: {
    loadKorisnici() {
      axios.get(`rest/user/users`).then((response) => {
        console.log(response.data);
        this.korisnici = response.data;
      });
    },
  },
});
