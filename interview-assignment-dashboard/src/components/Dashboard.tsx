import {
  Box,
  Container,
  FormControlLabel,
  Grid,
  Switch,
  TextField,
} from '@mui/material';
import { ChangeEvent, FormEvent, useState } from 'react';
import './dashboard.css';
import ShowData, { ResponseData } from './ShowData';

export default function Dashboard() {
  const ariaLabel = { 'aria-label': 'description' };

  interface FormData {
    countryCode?: string;
    searchMethod: string;
    searchValue: string;
    alternativeSource?: string;
    strictSearch?: boolean;
    searchLimit?: number | string;
  }

  const [isFormSubmitted, setIsFormSubmitted] = useState<boolean>(false);

  const [responseData, setResponseData] = useState<ResponseData>();

  const [formData, setFormData] = useState<FormData>({
    countryCode: '',
    searchMethod: '',
    searchValue: '',
    strictSearch: false,
    searchLimit: '',
  });

  const [error, setError] = useState<string | null>(null);

  function handleInputChange(event: ChangeEvent<HTMLInputElement>) {
    const { name, value, type, checked } = event.target;
    setFormData({
      ...formData,
      [name]:
        type === 'checkbox'
          ? checked
          : type === 'number'
          ? Number(value)
          : value,
    });
  }

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setError(null);
    try {
      const response = await fetch('http://localhost:8080/api/data', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(
          errorData.message || 'There was an error with the request!'
        );
      }

      const responseData = await response.json();
      setResponseData(responseData);
      setIsFormSubmitted(true);
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (error: any) {
      setIsFormSubmitted(false);
      setResponseData(undefined);
      console.log('There was an error with request!', error);
      setError(error.message || 'There was an error with the request!');
    }
  }

  return (
    <>
      <h1>Dashboard</h1>
      <Container maxWidth="sm">
        <Box mt={5}>
          <form onSubmit={handleSubmit}>
            <Grid container spacing={3}>
              <Grid item xs={12}>
                <TextField
                  name="countryCode"
                  type="text"
                  id="countryCode"
                  value={formData.countryCode}
                  onChange={handleInputChange}
                  inputProps={ariaLabel}
                  label="CountryCode"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  name="searchMethod"
                  type="text"
                  required
                  id="searchMethod"
                  value={formData.searchMethod}
                  onChange={handleInputChange}
                  inputProps={ariaLabel}
                  label="searchMethod"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  name="searchValue"
                  type="text"
                  required
                  id="searchValue"
                  value={formData.searchValue}
                  onChange={handleInputChange}
                  inputProps={ariaLabel}
                  label="searchValue"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  name="alternativeSource"
                  type="text"
                  id="alternativeSource"
                  value={formData.alternativeSource}
                  onChange={handleInputChange}
                  inputProps={ariaLabel}
                  label="alternativeSource"
                />
              </Grid>
              <Grid item xs={12}>
                <FormControlLabel
                  control={
                    <Switch
                      name="strictSearch"
                      id="strictSearch"
                      checked={formData.strictSearch}
                      onChange={handleInputChange}
                      inputProps={ariaLabel}
                    />
                  }
                  label="strictSearch"
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  name="searchLimit"
                  type="number"
                  id="searchLimit"
                  value={formData.searchLimit}
                  onChange={handleInputChange}
                  inputProps={ariaLabel}
                  label="searchLimit"
                />
              </Grid>
              <Grid item xs={12}>
                <button type="submit" id="submitButton">
                  Submit
                </button>
              </Grid>
            </Grid>
          </form>
        </Box>
      </Container>
      <div>
        {isFormSubmitted && responseData ? (
          <ShowData data={responseData} />
        ) : (
          error && <p>{error}</p>
        )}
      </div>
    </>
  );
}
